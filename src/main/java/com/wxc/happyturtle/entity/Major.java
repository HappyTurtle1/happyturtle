package com.wxc.happyturtle.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WXC on 2018/08/11.
 */
public class Major extends BasicEntity implements Serializable {

    private int id; //ID（专业对应序号）
    private String major_name; //专业名称
    private int status; //是否使用 0使用 1禁用 默认0
    private Date create_time;  //创建时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
