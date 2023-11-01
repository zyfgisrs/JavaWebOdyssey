package com.zhouyf.config;

import com.zhouyf.vo.Company;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:company.properties",encoding = "UTF-8")
public class CompanyConfig {
    @Bean
    public Company getCompany(){
        return new Company();
    }
}
