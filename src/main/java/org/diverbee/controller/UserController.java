package org.diverbee.controller;

import jakarta.validation.constraints.Pattern;
import org.diverbee.pojo.Result;
import org.diverbee.pojo.User;
import org.diverbee.service.UserService;
import org.diverbee.utils.JwtUtil;
import org.diverbee.utils.Md5Util;
import org.diverbee.utils.ThreadLocalUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvater(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd  = params.get("re_pwd");

        if(!java.util.regex.Pattern.matches("^\\S{5,16}",newPwd)){
            return Result.error("新密码格式不正确");
        }

        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要参数");
        }

        //原密码是否正确

        Map<String,Object> map =  ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User loginuser = userService.findByUserName(username);
        if(!loginuser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码不正确");
        }
        if(!newPwd.equals(rePwd)){
            return Result.error("两次填写的新密码不一样");
        }
        //更新密码

        userService.updatePwd(newPwd);
        return Result.success();
    }

}
