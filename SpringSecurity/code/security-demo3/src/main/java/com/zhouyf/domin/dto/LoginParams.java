package com.zhouyf.domin.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class LoginParams implements Serializable {
    private String username;

    private String password;
}
