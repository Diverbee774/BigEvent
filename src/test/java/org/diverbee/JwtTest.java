package org.diverbee;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtTest {

    @Test
    public void testGen(){

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", 1);
        claims.put("username", "admin");

        //生成jwt
        String token =  JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("diverbee_i_love_you"));
        System.out.println(token);
    }

    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImFkbWluIn0sImV4cCI6MTczNjc5NzUyNn0" +
                ".fIA7TzbD7Ia5org3cHWTzUFGExbrvRN46AqRDQTD3ss";
        JWTVerifier jwtVerifier =  JWT.require(Algorithm.HMAC256("diverbee_i_love_you")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Object> claims = decodedJWT.getClaim("user").asMap();

    }
}
