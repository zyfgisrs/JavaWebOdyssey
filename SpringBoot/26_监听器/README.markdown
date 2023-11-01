# 整合监听器

## 监听器

监听器允许你对特定的事件或状态变化作出反应。例如，在web应用程序中，你可能想要知道当应用程序启动或停止时发生了什么。

### Servlet API中的监听器

在原始的Servlet API中，有几种主要的监听器：

- **`ServletContextListener`**：监听web应用程序的生命周期。
- **`HttpSessionListener`**：监听session的生命周期。
- **`ServletRequestListener`**：监听request的生命周期。

这些监听器通过实现特定的接口并重写其方法来工作。

范例：

```java
package com.zhouyf.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebServerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("servlet初始化" + sce.getServletContext().getServerInfo());
    }
}
```

配置扫描组件：

```java
package com.zhouyf;

import com.zhouyf.banner.MyBanner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan({"com.zhouyf.listener"}) //servlet组件扫描
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(MyApplication.class);//获取实例化对象
        springApplication.run(args); //运行SpringBoot程序
    }
}
```

### SpringBoot中的监听器

虽然Servlet API提供了其自己的监听器，但Spring Boot也提供了一系列的事件和监听器，允许你在应用程序的生命周期中的关键时刻执行代码。

在Spring Boot中，以下是一些常见的事件：

- **ApplicationStartedEvent**：当Spring Boot应用启动并且Spring应用上下文被刷新之后触发此事件。这意味着所有的bean已经被加载。
- **ApplicationReadyEvent**：当应用程序已启动且准备好接受请求时触发。
- **ContextRefreshedEvent**：当应用程序上下文刷新（例如，所有Bean都已完全创建）时触发。
- **ApplicationFailedEvent**：如果应用程序启动失败，则发送此事件。

```java
package com.zhouyf.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyReadyListener {


    @EventListener
    public void handleStartEvent(ApplicationStartedEvent even){
        System.out.println("Application has started");
    }

    @EventListener
    public void handleRefreshEvent(ContextRefreshedEvent even){
        System.out.println("ApplicationContext is refreshed and beans are fully initialized via @EventListener!");
    }

    @EventListener
    public void handleReadyEvent(ApplicationReadyEvent event){
        System.out.println("application is ready to serve requests via @EventListener!");
    }
    
}
```

启动测试：

![image-20231030123818645](assets/image-20231030123818645.png)

监听器在不同阶段被触发