package com.zhouyf.action;

import com.zhouyf.service.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TokenAction {

    @Autowired
    private ITokenService tokenService;

    @RequestMapping("/token")
    public Object token(@RequestParam("code") String code) {
        String token = this.tokenService.createToken(code);
        log.info("【Token】生成客户端Token数据，code = {}、token = {}", code, token);
        return token;
    }
}
