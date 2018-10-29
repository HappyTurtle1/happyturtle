package com.wxc.happyturtle.controller;

import com.wxc.happyturtle.util.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by WXC on 2016/12/6.
 */
@Controller
public class VerifyImageController {


    /**
     *
     * @Title: verifyCode
     * @Description: 生成图片验证码
     * @param @param request
     * @param @param response
     * @return String 返回类型
     */
    @RequestMapping(value = "/verifyImage")
    public void verifyMsgCode(HttpServletRequest request, HttpServletResponse response){
        ImageUtil.generateVerifyImage(request, response,"vi_phone");
    }


}
