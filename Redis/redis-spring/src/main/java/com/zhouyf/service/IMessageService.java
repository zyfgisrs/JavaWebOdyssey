package com.zhouyf.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "zhouyf:message")
public interface IMessageService {
    @Cacheable(value = "one")
    public List<String> list1();


    @Cacheable(value = "all")
    public List<String> list2();
}
