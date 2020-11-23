package com.docsys.manager.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.docsys.manager.entity.User;
import com.docsys.manager.mapper.UserMapper;
import com.docsys.manager.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
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

    @Override
    public void updatePassword(String userName, String password) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name_pn", userName);

        User user = new User();
        user.setPassword(password); // 使用这种方式， 只会更新user 中存在的字段
        Integer rows = userMapper.update(user, updateWrapper);
    }
}
