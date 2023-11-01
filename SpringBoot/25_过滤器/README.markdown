# 过滤器配置

## 过滤器

### 什么是过滤器

- 过滤器是Java Servlet规范中定义的一个组件。它允许开发人员对请求和响应进行一些预处理和后处理。简单来说，当一个请求到来时，它首先会经过一个或多个过滤器，然后再到达目标Servlet或其他资源。在响应返回客户端之前，过滤器同样可以进行处理。

### 过滤器的工作原理

- 当用户发起请求时，它首先会到达服务器的Web容器（例如Tomcat）。
- 在请求到达目标资源之前，Web容器可以根据配置来决定是否要通过一个或多个过滤器。
- 这些过滤器按照在配置中定义的顺序执行。
- 每个过滤器可以决定是否继续处理链上的下一个组件，或者直接返回响应给客户端。

### doFilter方法

```java
void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException;
```

参数：

- **ServletRequest**：客户端的请求信息。
- **ServletResponse**：服务端的响应信息。
- **FilterChain**：过滤器链，用于调用下一个过滤器或目标资源（如 Servlet）。

工作流程：

1. **预处理**：当请求经过过滤器时，你可以在`doFilter`方法的开始部分对请求进行预处理。例如，检查请求头中的授权信息、修改请求参数等。
2. **链式处理**：通过调用`chain.doFilter(request, response)`，请求将被传递给下一个过滤器，或者当没有更多过滤器时，将传递给目标资源。
3. **后处理**：`chain.doFilter(request, response)`之后的代码将在响应返回到客户端之前执行。你可以在这里进行一些后处理，比如记录日志、修改响应头等。

示例：

假设我们想要实现一个简单的日志过滤器，它在接收到请求时记录请求的URL，在发送响应时记录响应的状态码。

```java
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        System.out.println("Request received for: " + httpRequest.getRequestURI());

        chain.doFilter(request, response); // 继续过滤器链或目标资源的执行

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        System.out.println("Response status: " + httpResponse.getStatus());
    }

    // 初始化和销毁方法...
}
```

### SpringBoot中的过滤器

尽管过滤器是Java Servlet规范的一部分，但在Spring Boot中，你可以轻松地定义和使用过滤器。Spring Boot并没有改变过滤器的核心概念，但它为创建和配置过滤器提供了更简单的方式。

### `@WebFilter` 注解：

`@WebFilter`是Java Servlet 3.0规范中引入的一个注解，它允许开发者以声明式的方式定义一个过滤器，而不再需要在`web.xml`中进行配置。

基本属性：

- **filterName**：过滤器的名称。
- **urlPatterns**：需要过滤的URL模式。
- **servletNames**：需要过滤的Servlet名称。
- **initParams**：初始化参数。
- **description**：过滤器描述。
- **displayName**：过滤器的显示名称。
- **value**：这是`urlPatterns`的简写。

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

