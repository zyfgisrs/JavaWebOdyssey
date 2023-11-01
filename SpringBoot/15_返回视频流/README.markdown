# 返回视频流

- 创建视频资源的请求处理器`com.zhouyf.config.handler.VideoResourceHttpRequestHandler`

```java
package com.zhouyf.config.handler;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Component
//这个类继承自ResourceHttpRequestHandler，这是Spring用于处理静态资源请求的类，例如图片、js、css文件等。
public class VideoResourceHttpRequestHandler extends ResourceHttpRequestHandler {
    //这个方法是ResourceHttpRequestHandler类中的一个方法，它被重写来提供自定义的功能。
    // 在此，它被用来返回一个指向特定视频文件的资源。
    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        return new ClassPathResource("/videos/video1.mp4");
    }
}
```

- 创建控制器`com.zhouyf.action.VideoAction`

```java
package com.zhouyf.action;

import com.zhouyf.config.handler.VideoResourceHttpRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/video/*")
public class VideoAction {
    @Autowired
    private VideoResourceHttpRequestHandler videoResourceHttpRequestHandler;

    @GetMapping("mp4")
    public void createVideo(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        //handleRequest方法是Spring框架用于处理静态资源请求的方法。
        // 它会根据请求的URL来定位资源，然后将资源内容写入响应中。
        this.videoResourceHttpRequestHandler.handleRequest(servletRequest, servletResponse);
    }
}
```

- 访问测试

```
http://localhost:8080/video/mp4
```

![image-20231021194353061](assets/image-20231021194353061.png)

成功返回了视频
