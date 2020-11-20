package com.docsys.manager.domain;

import lombok.Data;

@Data
public class User {
    String username;
    String password;
    String roles;
    String permission;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
