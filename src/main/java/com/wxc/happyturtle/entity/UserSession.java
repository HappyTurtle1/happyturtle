package com.wxc.happyturtle.entity;

import java.io.Serializable;

/**
 * Created by WXC on 2018/08/11.
 */
public class UserSession extends BasicEntity implements Serializable {

    private int id; //用户ID
    private String user_name; //用户名


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
