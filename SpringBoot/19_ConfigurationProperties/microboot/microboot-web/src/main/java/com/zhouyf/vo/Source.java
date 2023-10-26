package com.zhouyf.vo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
@Data
@ConfigurationProperties(prefix = "source")
public class Source {
    private String mysql;

    private String redis;

    private List<String> messages;

    private Map<String, String> info;
}
