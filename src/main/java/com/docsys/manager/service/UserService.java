package com.docsys.manager.service;

import com.docsys.manager.entity.User;

public interface  UserService {
    User getUserByPass(String userName, String password);

    User getUser(String userName);

    public void updatePassword(String userName,String password);
}
