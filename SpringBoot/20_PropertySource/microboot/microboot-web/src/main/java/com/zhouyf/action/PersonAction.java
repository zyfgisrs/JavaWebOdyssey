package com.zhouyf.action;

import com.zhouyf.vo.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/person/*", produces = MediaType.APPLICATION_XML_VALUE)
public class PersonAction {
    @RequestMapping("info")
    public Person echo(Person person){
        person.setAge(person.getAge() + 1);
        person.setName("【INFO】"+person.getName());
        return person;
    }
}
