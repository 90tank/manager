package com.docsys.manager;

import com.docsys.manager.entity.Permission;
import com.docsys.manager.entity.User;
import com.docsys.manager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void mybatis_plus_test() {
        int c =userService.updatePassword("15229282949","654321");
        System.out.println("----> "+c);
    }

    @Test
    public void user_insert(){
        User user = new User();
        user.setPassword("555555");
        user.setUserName("xxxxxmmmm");
        int id = userService.insertUser(user);
        System.out.println(id);
    }


    @Test
    public void user_list(){
        List<User> users =  userService.listUsers();
        System.out.println(users.get(0));
    }


    @Test
    public void test_permssion(){
        int userId = 1;
        List<Permission> permissions =  userService.getPermissions(userId);
        System.out.println(permissions.toString());
    }
}
