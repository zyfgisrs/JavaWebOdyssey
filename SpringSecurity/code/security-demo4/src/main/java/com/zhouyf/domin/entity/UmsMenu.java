package com.zhouyf.domin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ums_menu")
public class UmsMenu {

    @TableId
    private int id;
    private int parentId;
    private String menuName;
    private int status;
    private String perms;
    private int menuType;
    @TableLogic
    private int deleted;
}
