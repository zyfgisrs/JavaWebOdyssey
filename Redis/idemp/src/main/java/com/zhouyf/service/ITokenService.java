package com.zhouyf.service;

public interface ITokenService {

    public String createToken(String code);

    public boolean checkToken(String code, String token);
}
