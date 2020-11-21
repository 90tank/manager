package com.docsys.manager.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.docsys.manager.entity.User;
import com.docsys.manager.mapper.UserMapper;
import com.docsys.manager.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Mapper
    UserMapper userMapper;

    @Override
    public User getUserByPass(String userName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        Wrapper queryWrapper = new QueryWrapper(user);
        User theUser = userMapper.selectOne(queryWrapper);
        return theUser;
    }

    @Override
    public User getUser(String userName) {
        User user = new User();
        user.setUserName(userName);
        Wrapper queryWrapper = new QueryWrapper(user);
        User theUser = userMapper.selectOne(queryWrapper);
        return theUser;
    }
}
