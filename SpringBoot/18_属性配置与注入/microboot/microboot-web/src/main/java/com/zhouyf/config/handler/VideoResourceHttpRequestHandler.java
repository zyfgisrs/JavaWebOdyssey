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
