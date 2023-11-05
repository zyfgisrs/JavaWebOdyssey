# 拦截器

SpringMVC中的拦截器需要实现HandlerInterceptor接口来实现，同时根据自己的需求选择要覆写的方法。例如，在Action处理之前拦截则需要覆写preHandler()方法，而在方法中所有用户的请求信息都可以通过HandlerMethod类的对象实例获取。

- 定义拦截器`com.zhouyf.interceptor.DefaultHandlerInterceptor`

```java
package com.zhouyf.interceptor;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DefaultHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            System.out.println("action对象" + handlerMethod.getBean());
            System.out.println("action类型" + handlerMethod.getBeanType());
            System.out.println("action方法" + handlerMethod.getMethod());
        }
        return true;
    }
}
```

- 配置拦截器`com.zhouyf.config.WebInterceptorConfig`

```java
package com.zhouyf.config;

import com.zhouyf.interceptor.DefaultHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//表明这个类是一个配置类，它定义了Spring容器的bean配置
public class WebInterceptorConfig implements WebMvcConfigurer {
    @Override
    //这是WebMvcConfigurer接口中的一个方法，用于注册拦截器。
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截器是通过调用当前类中的getDefaultHandlerInterceptor方法获得的实例
        //这个方法链用于指定拦截器应该应用于哪些路径，/**表示拦截器将应用于所有路径。
        registry.addInterceptor(this.getDefaultHandlerInterceptor())
                .addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor getDefaultHandlerInterceptor() {
        // 创建并返回DefaultHandlerInterceptor的一个实例。
        return new DefaultHandlerInterceptor();
    }
}
```

- 访问测试

```
http://localhost:8080/message/echo?title=zhouyf&content=sdhjs&pubdate=2021-12-12
```

控制台打印

```
action对象com.zhouyf.action.MessageAction@69e476fe
action类型class com.zhouyf.action.MessageAction
action方法public com.zhouyf.vo.Message
com.zhouyf.action.MessageAction.echo(com.zhouyf.vo.Message)
```

