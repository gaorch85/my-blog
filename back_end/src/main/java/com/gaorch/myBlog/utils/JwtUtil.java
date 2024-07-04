package com.gaorch.myBlog.utils;

import com.gaorch.myBlog.entity.User;
import com.gaorch.myBlog.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static UserMapper userMapper = null;

    // 12小时过期
    private static final long expire = 3600 * 12;

    // 32位密钥
    private static final String secret = "ijdowudiehhudisodkpbdmckdisurnco";


    @Autowired
    public JwtUtil(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public static String generateToken(String account)
    {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * expire);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(account)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Claims getClaimsByToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getTokenByRequest(HttpServletRequest request)
    {
        return request.getHeader("X-token");
    }

    public static String getAccountByToken(String token)
    {
        return JwtUtil.getClaimsByToken(token).getSubject();
    }

    public static boolean isAuthrize(String token)
    {
        try {
            String account = JwtUtil.getClaimsByToken(token).getSubject();    //token不正确或者过期，抛出异常
            if (userMapper.selectByAccount(account) != null) {
                return true;
            } else
                return false;
        } catch (Exception e)
        {
            return false;
        }
    }





}
