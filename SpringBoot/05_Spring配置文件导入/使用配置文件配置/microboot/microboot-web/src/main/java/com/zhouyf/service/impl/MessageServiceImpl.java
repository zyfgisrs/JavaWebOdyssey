package com.zhouyf.service.impl;

import com.zhouyf.service.ImessageService;


public class MessageServiceImpl implements ImessageService {
    @Override
    public String echo(String msg) {
        return "[ECHO]" + msg;
    }
}
