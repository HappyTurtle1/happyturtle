package com.wxc.happyturtle.service;

import com.wxc.happyturtle.entity.LoginUser;
import com.wxc.happyturtle.mapper.LoginUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by WXC on 2018/08/11.
 */
@Service
public class LoginUserService {
    @Autowired
    private LoginUserMapper loginUserMapper;


    //插入用户
    public void insertLoginUser(LoginUser loginUser){
        loginUserMapper.insertLoginUser(loginUser);
    }


    //根据用户名查询用户
    public LoginUser selectLoginUser(String userName){
        return loginUserMapper.selectLoginUser(userName);
    }

    //更新登录时间
    public void updateLoginUser(int id){
        loginUserMapper.updateLoginUser(id);
    }

}
