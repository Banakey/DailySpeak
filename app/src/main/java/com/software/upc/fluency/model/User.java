package com.software.upc.fluency.model;

import com.software.upc.fluency.db.NewFriend;

import cn.bmob.v3.BmobUser;

/**
 * Created by jmy on 2017/4/8.
 */

public class User extends BmobUser {
    private String avatar;

    public User(){}

    public User(NewFriend friend){
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
//    private String username;
//    private String password;
//    private String email;
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public void setEmail(String email) {
//        this.email = email;
//    }
}

