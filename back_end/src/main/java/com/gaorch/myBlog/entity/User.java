package com.gaorch.myBlog.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String account;
    private String username;
    private String password;
    private String salt;

    public String getUsername()
    {
        return this.username != null ? this.username : "user_" + this.account;
    }
}
