package com.zhouyf.action;

import com.zhouyf.vo.Message;
import com.zhouyf.vo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

@RestController
@RequestMapping(value = "/person/*", produces = MediaType.APPLICATION_XML_VALUE)
public class PersonAction {
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonAction.class);


    @RequestMapping("info")
    public Person echo(Person person) {
        List<HttpMessageConverter<?>> converters = requestMappingHandlerAdapter.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter.canWrite(Person.class, MediaType.APPLICATION_XML)) {
                LOGGER.info("Using converter: " + converter.getClass().getSimpleName());
                break;
            }
        }

        for (HttpMessageConverter<?> converter : converters) {
            LOGGER.info("current: " + converter.getClass().getSimpleName());
        }
        person.setAge(person.getAge() + 1);
        person.setName("【INFO】" + person.getName());
        return person;
    }
}
