package com.zhouyf.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@Configuration是一个注解，它表明随后的类是一个配置类，Spring容器会特别对待。
@EnableWebMvc
//主要作用是启用 Spring MVC 的配置
public class WebConfig implements WebMvcConfigurer {
    //这行代码定义了一个名为WebConfig的公共类，该类实现了WebMvcConfigurer接口。
    // 通过实现此接口，可以自定义Web应用程序中的多个配置。
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //重写了WebMvcConfigurer接口中的configureMessageConverters方法。
        // 这个方法用于自定义HTTP消息转换器。

        //创建FastJsonHttpMessageConverter的一个实例。
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter =
                new FastJsonHttpMessageConverter();
        //创建FastJsonConfig的一个实例，以便能够自定义JSON的序列化和反序列化配置。
        FastJsonConfig config = new FastJsonConfig();
        //这段代码调用setSerializerFeatures方法来设置各种JSON序列化选项
        // 例如如何处理空值、日期格式、循环引用等。
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,//允许Map内容为null
                SerializerFeature.WriteNullListAsEmpty,//List集合为null则使用[]代替
                SerializerFeature.WriteNullStringAsEmpty,//String内容为空使用空字符代替
                SerializerFeature.WriteDateUseDateFormat, //日期格式化输出
                SerializerFeature.WriteNullNumberAsZero, //数字为空使用0
                SerializerFeature.DisableCircularReferenceDetect //禁用循环引用
        );
        //将自定义的FastJsonConfig配置设置到fastJsonHttpMessageConverter转换器实例中。
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        //转换器将仅用于JSON类型的数据
        List<MediaType> fastjsonMediaTypes = new ArrayList<>();
        fastjsonMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastjsonMediaTypes);
        //这行代码将配置好的FastJSON消息转换器添加到转换器列表中。
        // 这确保了Spring会使用这个转换器来处理JSON消息。
        converters.add(fastJsonHttpMessageConverter);

        //创建一个MappingJackson2XmlHttpMessageConverter实例
        // 添加 XML 消息转换器
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        converters.add(xmlConverter);
    }
}
