package com.docsys.manager.service;

import com.docsys.manager.domain.User;

public interface  UserService {
    User getUserByPass(String userName, String password);

    User getUser(String userName);
}
