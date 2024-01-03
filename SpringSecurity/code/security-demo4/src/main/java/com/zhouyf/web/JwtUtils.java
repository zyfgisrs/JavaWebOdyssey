package com.zhouyf.web;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
    SecretKey key = alg.key().build();

    public String createToken(Map<String, Object> map) {

        String token =  Jwts.builder()
                .signWith(key)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .claims(map)
                .compact();

        return token;
    }

    public Claims parseTokens(String token) {
        return Jwts.parser().verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
