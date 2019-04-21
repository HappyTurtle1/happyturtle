package com.wxc.happyturtle.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WXC on 2018/08/11.
 */
public class Person extends BasicEntity implements Serializable {

    private int id; //ID（专业对应序号）
    private String person_name; //姓名
    private int major_id; //所属专业ID
    private String phone; //电话
    private Date create_time;  //创建时间

    //非表内字段
    private String major_name; //专业名称

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public int getMajor_id() {
        return major_id;
    }

    public void setMajor_id(int major_id) {
        this.major_id = major_id;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
