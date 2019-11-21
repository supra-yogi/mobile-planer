package com.skripsi.yogi.planner.Domain.Users;

import com.skripsi.yogi.planner.Common.EntityBase;

public class User extends EntityBase {
    private String username;
    private String email;
    private String password;
    private boolean isChangePassword;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(){};

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isChangePassword() {
        return isChangePassword;
    }

    public void setChangePassword(boolean changePassword) {
        isChangePassword = changePassword;
    }
}
