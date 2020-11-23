package com.docsys.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.docsys.manager.common.Result;
import com.docsys.manager.entity.User;
import com.docsys.manager.mapper.UserMapper;
import com.docsys.manager.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class SysUserController {

    @Autowired
    UserService userService;

    @PostMapping("/update/password")
    @RequiresAuthentication
    public Result updateUserPassword(@RequestBody JSONObject requestJson) {
        String userId = requestJson.getString("userId");
        String password = requestJson.getString("password");

        int result = userService.updatePasswordById(userId, password);
        if (result>0) {
            return Result.succ(result);
        }

        return Result.fail("Update password failed");
    }


    @PostMapping("/update/changebind")
    @RequiresAuthentication
    public Result updateUserName_PhoneNum(@RequestBody JSONObject requestJson) {
        String userId = requestJson.getString("userId");
        String newPhoneNum = requestJson.getString("username");

        int result = userService.updatePhoneNumById(userId, newPhoneNum);
        if (result>0) {
            return Result.succ(result);
        }

        return Result.fail("Update password failed");
    }

    @PostMapping("/add")
    @RequiresAuthentication
    public Result addUser(@RequestBody JSONObject requestJson) {
        User user = (User)JSONObject.parse(String.valueOf(requestJson));
        int id = userService.insertUser(user);
        return Result.succ(id);
    }


    @PostMapping("/delete")
    @RequiresAuthentication
    public Result deleteUser(@RequestBody JSONObject requestJson) {
        User user = (User)JSONObject.parse(String.valueOf(requestJson));
        int id = userService.deleteUser(user);
        return Result.succ(id);
    }


    @PostMapping("/list")
    @RequiresAuthentication
    public Result listUser(@RequestBody JSONObject requestJson) {
        List<User> users =  userService.listUsers();
        return Result.succ(users);
    }

    @PostMapping("/get/permissions")
    @RequiresAuthentication
    public Result getUserPermissions(@RequestBody JSONObject requestJson) {
        String userId = requestJson.getString("userId");
        List<String> permissions = userService.getPermissions();
        return Result.succ(permissions);
    }

    @PostMapping("/get/menus")
    @RequiresAuthentication
    public Result getUserPermissions(@RequestBody JSONObject requestJson) {
        String userId = requestJson.getString("userId");
        List<String> permissions = userService.getMenus();
        return Result.succ(permissions);
    }
}
