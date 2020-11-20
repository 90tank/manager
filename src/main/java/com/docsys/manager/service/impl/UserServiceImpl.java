package com.docsys.manager.service.impl;

import com.docsys.manager.domain.User;
import com.docsys.manager.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByPass(String userName, String password) {
        // TODO 要去数据库查询
        return new User("zy","myspassword");
    }

    @Override
    public User getUser(String userName) {
        return null;
    }
}
