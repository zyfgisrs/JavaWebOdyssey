package com.zhouyf.config;


import com.zhouyf.vo.Source;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SourceConfig {

    @Bean
    public Source createSource(){
        return new Source();
    }
}
