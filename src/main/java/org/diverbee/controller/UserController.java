package org.diverbee.controller;

import jakarta.validation.constraints.Pattern;
import org.diverbee.pojo.Result;
import org.diverbee.pojo.User;
import org.diverbee.service.UserService;
import org.diverbee.utils.JwtUtil;
import org.diverbee.utils.Md5Util;
import org.diverbee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}") String username,@Pattern(regexp = "^\\S{5,16}") String password) {
        //查询用户
        User user = userService.findByUserName(username);
        if(user==null) {
            //注册
            userService.register(username, password);
            return Result.success();
        }else{
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}") String username,@Pattern(regexp = "^\\S{5,16}") String password){
        //查询用户
        User user = userService.findByUserName(username);
        if(user==null) {
            return Result.error("用户名不存在");
        }else if(!user.getPassword().equals(Md5Util.getMD5String(password))){
            return Result.error("密码错误");
        }else{

            Map<String,Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
//        Map<String,Object> claims = JwtUtil.parseToken(token);

        Map<String,Object> claims = ThreadLocalUtil.get();

        String name =(String) claims.get("username");

        User user = userService.findByUserName(name);
        return Result.success(user);

    }
}
