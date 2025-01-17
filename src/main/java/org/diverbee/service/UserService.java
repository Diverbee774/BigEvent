package org.diverbee.service;

import org.diverbee.pojo.User;

public interface UserService {

    //根据用户名查询用户
    User findByUserName(String username);

    //注册
    void register(String username, String password);

    //更新个人信息
    void update(User user);

    //更新头像
    void updateAvater(String avatarUrl);

    void updatePwd(String newPwd);
}
