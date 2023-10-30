# 过滤器配置

## 过滤器

### 什么是过滤器

在Web开发中，过滤器主要用于在请求到达目标资源（Controller等）之前，或在响应返回客户端之前，执行某些操作。

### SpringBoot中的过滤器

尽管过滤器是Java Servlet规范的一部分，但在Spring Boot中，你可以轻松地定义和使用过滤器。Spring Boot并没有改变过滤器的核心概念，但它为创建和配置过滤器提供了更简单的方式。

### 过滤器的常见用途

- **日志记录**：记录请求的详细信息，如URL、IP地址和响应时间。
- **验证和授权**：例如，检查用户是否登录，或是否有权限访问某个资源。
- **修改请求和响应**：例如，添加、修改或删除请求/响应头。
- **错误处理**：捕获请求处理过程中的异常，并进行适当的处理。

## 定义过滤器

- 将过滤器类定义在filter包下

```java
package com.zhouyf.filter;

import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class MessageFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ("/message/echo".equals(request.getRequestURI())){
            String title = request.getParameter("title");
            if (StringUtils.hasLength(title)){
                System.out.println("【MessageFilter】title参数内容为:" + title);
            }
        }

        chain.doFilter(request, response);
    }
}
```

- 启动类加上`@ServletComponentScan`注解

```java
package com.zhouyf;

import com.zhouyf.banner.MyBanner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan //servlet组件扫描
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(MyApplication.class);//获取实例化对象
        springApplication.setBanner(new MyBanner());//配置自定义Banner的生成器
        springApplication.setBannerMode(Banner.Mode.CONSOLE); //配置Banner输出到控制台
        springApplication.run(args); //运行SpringBoot程序
    }
}
```

## 测试

```
http://localhost:8080/message/echo?title=zhouyf&pubdate=2021-11-11&content=zhouyangfan
```

![image-20231030103526461](assets/image-20231030103526461.png)

