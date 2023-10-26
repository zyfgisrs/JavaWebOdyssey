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
