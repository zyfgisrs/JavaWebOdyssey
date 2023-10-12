package com.zhouyf;

import com.zhouyf.banner.MyBanner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource(locations = {"classpath:META-INFO/spring/spring-*.xml"})
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication springApplication =
                new SpringApplication(MyApplication.class);//获取实例化对象
        springApplication.setBanner(new MyBanner());//配置自定义Banner的生成器
        springApplication.setBannerMode(Banner.Mode.CONSOLE); //配置Banner输出到控制台
        springApplication.run(args); //运行SpringBoot程序
    }
}
