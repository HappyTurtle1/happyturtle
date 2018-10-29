package com.wxc.happyturtle.mapper;

import com.wxc.happyturtle.entity.LoginUser;
import org.apache.ibatis.annotations.*;


/**
 * Created by WXC on 2018/08/11.
 */
@Mapper
public interface LoginUserMapper {


    @Insert("insert into login_user(user_name,real_name,user_pwd,live_status,user_phone,email,create_time,login_time) " +
            "values(#{user_name},#{real_name},#{user_pwd},#{live_status},#{user_phone},#{email},#{create_time},#{login_time})")
    void insertLoginUser(LoginUser loginUser);

    @Select("select * from login_user where user_name = #{user_name}")
    LoginUser selectLoginUser(String user_name);

    @Update("update login_user set login_time = now() where id = #{id}")
    void updateLoginUser(int id);

}