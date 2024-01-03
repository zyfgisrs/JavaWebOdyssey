package com.zhouyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhouyf.domin.dto.LoginParams;
import com.zhouyf.domin.entity.UmsSysUser;

public interface IUmsSysUser extends IService<UmsSysUser> {
    public String login(LoginParams loginParams);
}
