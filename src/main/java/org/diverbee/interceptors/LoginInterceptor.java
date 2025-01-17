package org.diverbee.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.diverbee.utils.JwtUtil;
import org.diverbee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的token
        String token = request.getHeader("Authorization");
        try {

            //验证token
            Map<String, Object> claims = JwtUtil.parseToken(token);


            //从redis中获取一模一样的token

            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String redisToken = operations.get((String)claims.get("username"));
            if(redisToken==null||!redisToken.equals(token)){ //token已经失效
                throw new RuntimeException();
            }


            //将用户信息存入ThreadLocal
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            //http响应状态码为401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除ThreadLocal
        ThreadLocalUtil.remove();
    }
}
