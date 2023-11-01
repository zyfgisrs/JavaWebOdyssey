package com.zhouyf.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Company {
    @Value("${company.cid}")
    private Long cid;

    @Value("${company.cname}")
    private String cname;
}
