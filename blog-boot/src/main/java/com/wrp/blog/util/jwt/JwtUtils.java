package com.wrp.blog.util.jwt;

import com.wrp.blog.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.jsonwebtoken.Claims.SUBJECT;

/**
 * jwt工具，生成、校验token
 * @author wrp
 * @since 2024-09-09 07:50
 **/
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;
    String TOKEN_PREFIX = "Bearer ";


    public String getTokenName() {
        return jwtProperties.getTokenName();
    }

    /**
     * 创建JWT
     * @param user 用户信息
     * @return token
     */
    public String createJWT(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, user.getUsername());
        return TOKEN_PREFIX + createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);
    }

    /**
     * 生成jwt
     * 使用Hs256算法，私钥使用固定密钥
     * @param secretKey  jwt密钥
     * @param ttlMillis  jwt过期时间，单位毫秒
     * @param claims     设置的信息
     * @return token
     */
    private String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims){
        //指定加密算法
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;
        //生成JWT的时间
        long expMillis = System.currentTimeMillis()+ttlMillis;
        Date exp = new Date(expMillis);
        //密钥实例
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        String compact = Jwts.builder()
                .signWith(key, algorithm) //设置签名使用的签名算法和签名使用的秘钥
                .expiration(exp)//设置过期时间
                .claims(claims) //设置自定义负载信息
                .compact();
        return compact;
    }

    /**
     * 解析token
     * @param token 令牌信息
     * @return username
     */
    public String parseJWT(String token) {
        return parseJWT(token.replace(TOKEN_PREFIX, ""), jwtProperties.getSecretKey());
    }

    /**
     * 解析jwt
     * @param token 令牌信息
     * @param secretKey 密钥
     * @return username
     */
    private String parseJWT(String token, String secretKey){
        //密钥实例
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(key)  //设置签名的密钥
                .build()
                .parseSignedClaims(token); //设置要解析的jwt
        Claims claims = claimsJws.getPayload();
        return claims.getSubject();
    }
}
