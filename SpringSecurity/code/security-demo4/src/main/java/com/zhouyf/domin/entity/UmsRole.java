package com.zhouyf.domin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@TableName("ums_role")
public class UmsRole implements Serializable {

    @TableId
    private int id;
    private String roleLabel;
    private String roleName;
    private int status;
    @TableLogic
    private int deleted;
    private String remark;
}
