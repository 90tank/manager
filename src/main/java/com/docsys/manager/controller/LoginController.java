package com.docsys.manager.controller;


import com.docsys.manager.common.Result;

import com.docsys.manager.entity.User;
import com.docsys.manager.jwt.JWTUtil;
import com.docsys.manager.redis.RedisUtil;
import com.docsys.manager.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${token.redisExpireTime}")
    private int redisExpireTime;

    @PostMapping("/login")
    public Result login(@RequestBody JSONObject requestJson){
        String username = requestJson.getString("username");
        String password = requestJson.getString("password");

        User user=userService.getUserByPass(username, password);
        Assert.notNull(user,"用户名或密码错误");
        long currentTimeMillis = System.currentTimeMillis();
        String token= JWTUtil.createToken(user.getUserName(),currentTimeMillis);
        redisUtil.set(username,currentTimeMillis,redisExpireTime);
        return Result.succ(200,"登陆成功",token);
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public Result unauthorized(@PathVariable String message) throws UnsupportedEncodingException {
        return Result.fail(message);
    }

    @DeleteMapping("/logout")
    @RequiresAuthentication
    public Result logout(HttpServletRequest request){
        String token=request.getHeader("Authorization");
        String username=JWTUtil.getUsername(token);
        redisUtil.del(username);
        return Result.succ(null);
    }

    @PostMapping("/testdemo")
    @RequiresAuthentication
    //@RequiresAuthentication // 加上处理逻辑没有问题，如果不加,经过过滤器处理后 方法内部的逻辑还是会被执行
    // 所以，在使用的时候，对需要经过认证才能做得操作，一定加上 @RequiresAuthentication
    public void testdemo() {
        System.out.println("hahahaha");
        System.out.println("tttttt---->>>>>>>>");
    }
}

