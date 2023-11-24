package com.zhouyf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.env.StandardEnvironment;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSettings {
    private Long id;
    private Long userId;
    private String theme;

}
