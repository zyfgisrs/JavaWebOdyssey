package com.zhouyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhouyf.domin.dto.LoginParams;
import com.zhouyf.domin.entity.UmsSysUser;

public interface IUmsSysUserService extends IService<UmsSysUser> {
    String login(LoginParams loginParams);
}
