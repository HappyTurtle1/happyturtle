package com.wxc.happyturtle.controller;

import com.wxc.happyturtle.Constants;
import com.wxc.happyturtle.api.ApiResponse;
import com.wxc.happyturtle.entity.LoginUser;
import com.wxc.happyturtle.entity.UserSession;
import com.wxc.happyturtle.service.LoginUserService;
import com.wxc.happyturtle.util.IpUtil;
import com.wxc.happyturtle.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private LoginUserService loginUserService;

    //进入登录界面
    @GetMapping("/login")
    public String toLogin(HttpServletRequest request) {

        UserSession userSession = (UserSession) request.getSession().getAttribute("user");

        if(userSession != null){
            return "redirect:/";
        }

        logger.info(IpUtil.getIpAddr(request)+"访问页面。");
        return "web/auth/login";
    }


    //用户登录
    @PostMapping("/login")
    @ResponseBody
    public ApiResponse login(HttpServletRequest request) {

        ApiResponse result = new ApiResponse();

        String user_name = StringUtil.parseStringParam(request.getParameter("userName"));
        String user_pass = StringUtil.parseStringParam(request.getParameter("userPwd"));
        if(user_name == null || user_pass == null){
            logger.warn("用户"+user_name+"登录用户名和密码不能为空！");
            result.setCode(1);
            result.setMessage("用户名和密码不能为空！");
            return result;
        }

        LoginUser loginUser = loginUserService.selectLoginUser(user_name);

        if (null == loginUser){
            logger.warn("用户"+user_name+"不存在！");
            result.setCode(1);
            result.setMessage("用户不存在，请注册后再登录！");
            return result;
        }

        if(!user_pass.equals(loginUser.getUser_pwd())){
            logger.warn("用户"+user_name+"密码错误！");
            result.setCode(1);
            result.setMessage("用户密码错误！");
            return result;
        }

        //更新登录时间
        loginUserService.updateLoginUser(loginUser.getId());

        UserSession userSession = new UserSession();
        userSession.setId(loginUser.getId());
        userSession.setUser_name(loginUser.getUser_name());

        request.getSession().setAttribute("user", userSession);
        request.getSession().setMaxInactiveInterval(Constants.USER_SESSION_MAX_INTERVAL);
        logger.info("用户"+user_name+"登录成功！");

        result.setCode(0);
        result.setMessage("登录成功！");
        return result;
    }


    //进入注册界面
    @GetMapping("/register")
    public String toRegister(HttpServletRequest request) {

        UserSession userSession = (UserSession) request.getSession().getAttribute("user");

        if(userSession != null){
            return "redirect:/";
        }

        logger.info(IpUtil.getIpAddr(request)+"访问页面。");
        return "web/auth/register";
    }

    //用户注册
    @PostMapping("/register")
    @ResponseBody
    public ApiResponse register(HttpServletRequest request) {


        String user_name = StringUtil.parseStringParam(request.getParameter("userName"));
        String user_pass = StringUtil.parseStringParam(request.getParameter("userPwd"));
        String msg_code = StringUtil.parseStringParam(request.getParameter("msgCode"));
        String real_name = StringUtil.parseStringParam(request.getParameter("realName"));
        String phone = StringUtil.parsePhoneParam(request.getParameter("phone"));
        String email = StringUtil.parseStringParam(request.getParameter("email"));

        ApiResponse result = new ApiResponse();

        if(user_name == null || user_pass == null || msg_code == null || real_name == null || phone == null || email == null){
            logger.warn("参数错误！");
            result.setCode(1);
            result.setMessage("参数错误！");
            return result;
        }



        LoginUser loginUser = loginUserService.selectLoginUser(user_name);

        if (null != loginUser){
            logger.warn("用户"+user_name+"已存在！");
            result.setCode(1);
            result.setMessage("用户已存在，请直接再登录！");
            return result;
        }

        //String imgCode = (String) request.getSession().getAttribute("vi_phone");// 验证图片验证码

        String imgCode = "1234";

        if(null == imgCode || !imgCode.equals(msg_code)){
            logger.warn("图片验证码错误！");
            result.setCode(1);
            result.setMessage("图片验证码错误！");
            return result;
        }

        loginUser = new LoginUser();
        loginUser.setUser_name(user_name);
        loginUser.setReal_name(real_name);
        loginUser.setUser_pwd(user_pass);
        loginUser.setUser_phone(phone);
        loginUser.setEmail(email);
        loginUser.setLive_status(0);
        loginUser.setCreate_time(new Date());
        loginUser.setLogin_time(new Date());

        loginUserService.insertLoginUser(loginUser);

        UserSession userSession = new UserSession();
        userSession.setId(loginUser.getId());
        userSession.setUser_name(loginUser.getUser_name());

        request.getSession().setAttribute("user", userSession);
        request.getSession().setMaxInactiveInterval(Constants.USER_SESSION_MAX_INTERVAL);
        logger.info("用户"+user_name+"注册成功！");

        result.setCode(0);
        result.setMessage("注册成功！");
        return result;
    }
}
