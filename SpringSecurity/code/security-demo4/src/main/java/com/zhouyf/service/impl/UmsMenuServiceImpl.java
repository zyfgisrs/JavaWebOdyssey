package com.zhouyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhouyf.domin.entity.UmsMenu;
import com.zhouyf.mapper.UmsMenuMapper;
import com.zhouyf.service.IUmsMenuService;
import org.springframework.stereotype.Service;

@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {
}
