package com.token.authenticate.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    public static String getUserName(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get("userName", String.class); // string으로 userName을 꺼내온다.
    }
    public static boolean isExpired(String token, String secretKey){
        // token의 만료가 현재 date보다 이전인지 확인
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
    public static String createToken(String userName, String key, long expiredTimeMs){
        Claims claims = Jwts.claims(); // 일종의 map
        claims.put("userName",userName);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact()
                ;
    }
}
