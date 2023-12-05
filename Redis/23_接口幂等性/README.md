# 接口幂等性

接口的幂等性是一个在计算机科学和网络编程中非常重要的概念，尤其在分布式系统和RESTful API设计中具有重要意义。简单来说，一个接口具有幂等性指的是，无论这个接口被调用多少次，产生的副作用（对系统状态的影响）都是一样的。

## 接口幂等性的重要性

1. **错误恢复和重试机制**：在网络不稳定或系统故障的情况下，客户端可能需要重试同一请求。如果接口是幂等的，重试不会导致不期望的副作用或状态变化。
2. **简化系统设计**：理解和设计幂等接口可以简化系统架构，因为开发者可以放心地重复调用接口，而不必担心额外的副作用。

## 范例

这套系统通过结合使用Redis、Spring框架和自定义注解实现了幂等性。首先，`TokenServiceImpl`类生成一个唯一的Token，并将其与特定的代码关联存储在Redis中。这个Token有一个固定的过期时间（在这个例子中是30秒）。当客户端发起请求时，它必须携带这个Token。然后，`IdempotentInterceptor`拦截器介入，它检查所有传入的请求，寻找标有`@Idempontent`注解的方法。当找到这样的方法时，拦截器会验证请求中的Token和代码是否匹配，且Token是否仍然有效。如果验证成功，请求就会被处理，同时Token会从Redis中移除，以防止同一个Token被用于多个请求。这样，即使客户端由于网络延迟或其他原因重复发送同一请求，服务器也只会处理一次，从而实现了幂等性。

### `TokenServiceImpl`类

`createToken`为给定的代码生成一个唯一的Token，如果该代码已经有了Token，则不会创建新的。

`checkToken`验证给定代码和Token的配对是否有效，如果有效则删除该Token，防止再次使用。

```java
package com.zhouyf.service.impl;

import com.zhouyf.service.ITokenService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements ITokenService {

    private static final String TOKEN_PREFIX = "zhouyfToken:";

    private static final long EXPIRE_TIME = 30; //设置过期时间


    final StringRedisTemplate stringRedisTemplate;

    public TokenServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String createToken(String code) {
        String key = TOKEN_PREFIX + code;
        if(this.stringRedisTemplate.hasKey(key)){
            return null;
        }
        String value = UUID.randomUUID().toString();
        this.stringRedisTemplate.opsForValue().setIfAbsent(
                key, value, EXPIRE_TIME, TimeUnit.SECONDS
        );
        return value;
    }

    @Override
    public boolean checkToken(String code, String token) {
        String key = TOKEN_PREFIX + code;
        String value = this.stringRedisTemplate.opsForValue().get(key);
        if(value != null){
            if(value.equals(token)){
                this.stringRedisTemplate.delete(key);
                return true;
            }
        }
        return false;
    }
}
```

### **Idempotent Annotation**

```java
package com.zhouyf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempontent { //token的检查

}
```

### **Interceptor** 拦截器

```java
package com.zhouyf.interceptor;

import com.zhouyf.annotation.Idempontent;
import com.zhouyf.service.ITokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;



@Slf4j
@Component
public class IdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private ITokenService iTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Idempontent idempontent = method.getAnnotation(Idempontent.class);
        //如果在实际开发中，发现token不正确或者是code有问题，应该返回错误信息
        if(idempontent != null){//方法上已经标记了所需的注解
            return check(request);
        }
        return true;
    }

    private boolean check(HttpServletRequest request){
        String token = request.getHeader("token");
        if(token == null || token.isEmpty()){
            token = request.getParameter("token");
            if(token == null || token.isEmpty()){
                log.error("【幂等性检测】检测失败，没有传递token数据。");
            }
        }

        String code = request.getParameter("code");
        if(token != null && code != null){
            return this.iTokenService.checkToken(code, token);
        }
        return false;
    }
}
```

### 拦截器配置类

```java
package com.zhouyf.config;

import com.zhouyf.interceptor.IdempotentInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.handlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor handlerInterceptor() {
        return new IdempotentInterceptor();
    }
}

```

### 控制器

```java
package com.zhouyf.action;

import com.zhouyf.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TokenAction {

    @Autowired
    private ITokenService tokenService;

    @RequestMapping("/token")
    public Object token(@RequestParam("code") String code) {
        String token = this.tokenService.createToken(code);
        log.info("【Token】生成客户端Token数据，code = {}、token = {}", code, token);
        return token;
    }
}
```

```java
package com.zhouyf.action;


import com.zhouyf.annotation.Idempontent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MessageAction {

    @RequestMapping("/echo")
    @Idempontent
    public Object echo(@RequestParam("msg") String msg) {
        return "【ECHO】" + msg;
    }
}
```

