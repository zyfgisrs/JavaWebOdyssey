package com.zhouyf.vo;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class Person {
    @XmlElement
    private int age;
    @XmlElement
    private String name;
}
