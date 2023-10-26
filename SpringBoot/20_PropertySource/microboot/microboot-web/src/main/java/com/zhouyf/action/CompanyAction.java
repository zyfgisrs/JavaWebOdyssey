package com.zhouyf.action;

import com.zhouyf.vo.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company/*")
public class CompanyAction {
    @Autowired
    private Company company;

    @GetMapping(name = "company", produces = MediaType.APPLICATION_JSON_VALUE)
    public Company show(){
        return company;
    }
}
