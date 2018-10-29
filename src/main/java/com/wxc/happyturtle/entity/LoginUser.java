package com.wxc.happyturtle.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WXC on 2018/08/11.
 */
public class LoginUser extends BasicEntity implements Serializable {

    private int id; //用户ID
    private String user_name; //用户名
    private String real_name; //真实姓名
    private String user_pwd;  //密码
    private int live_status;  //生存状态(0=可用 1=不可用)
    private String user_phone; //手机号
    private String email;     //邮箱
    private Date create_time;  //创建时间
    private Date login_time;  //最近登录时间


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

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public int getLive_status() {
        return live_status;
    }

    public void setLive_status(int live_status) {
        this.live_status = live_status;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getLogin_time() {
        return login_time;
    }

    public void setLogin_time(Date login_time) {
        this.login_time = login_time;
    }
}
