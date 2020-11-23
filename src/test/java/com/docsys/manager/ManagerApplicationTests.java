package com.docsys.manager;


import com.docsys.manager.service.UserService;
import com.docsys.manager.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerApplicationTests {


    @Autowired
    private UserService userService;


    @Test
    public void mybatis_plus_test(){

        userService.updatePassword("15229282949","654321");
    }

}
