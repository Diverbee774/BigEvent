package org.diverbee.service.impl;

import org.diverbee.mapper.UserMapper;
import org.diverbee.pojo.User;
import org.diverbee.service.UserService;
import org.diverbee.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5Password = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username, md5Password);
    }
}
