package com.zhouyf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        //创建一个MappingJackson2XmlHttpMessageConverter实例
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        //添加 XML 消息转换器
        converters.add(xmlConverter);

        //添加图片转器
        BufferedImageHttpMessageConverter imageConverter = new BufferedImageHttpMessageConverter();
        converters.add(imageConverter);
    }
}
