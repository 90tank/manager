package com.docsys.manager.service;

import com.docsys.manager.entity.Permission;
import com.docsys.manager.entity.User;

import java.util.List;

public interface  UserService {
    User getUserByPass(String userName, String password);

    User getUser(String userName);

    int updatePassword(String userName,String password);

    int updatePasswordById(String userId,String password);

    int updatePhoneNumById(String userId,String newPhoneNum);

    int insertUser(User user);

    int deleteUser(User user);

    List<User> listUsers();

    List<Permission> getPermissions(int userIdPk);

}
