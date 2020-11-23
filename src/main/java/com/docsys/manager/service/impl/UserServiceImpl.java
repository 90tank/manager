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

import java.util.List;

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
    public int updatePassword(String userName, String password) {

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_name_pn", userName);

        User user = new User();
        user.setPassword(password); // 使用这种方式， 只会更新user 中存在的字段
        Integer rows = userMapper.update(user, updateWrapper);
        return rows.intValue();
    }

    @Override
    public int updatePasswordById(String userId, String password) {
        User user = new User();
        user.setId(Integer.valueOf(userId)); // PK
        user.setPassword(password);

        return userMapper.updateById(user);
    }

    @Override
    public int updatePhoneNumById(String userId, String newPhoneNum) {
        User user = new User();

        user.setId(Integer.valueOf(userId)); // PK
        user.setPassword(newPhoneNum);
        return userMapper.updateById(user);
    }

    @Override
    public int insertUser(User user) {
        userMapper.insert(user);
        Integer id = user.getId();
        return id.intValue();
    }

    @Override
    public int deleteUser(User user) {
        return  userMapper.deleteById(user.getId());
    }

    @Override
    public List<User> listUsers() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("delete_status",0);
        List<User> users = userMapper.selectList(queryWrapper);
        return users;
    }

    @Override
    public List<String> getPermissions() {
        // select form
        return null;
    }
}
