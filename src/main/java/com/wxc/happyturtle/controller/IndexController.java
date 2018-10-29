package com.wxc.happyturtle.controller;

import com.wxc.happyturtle.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping({"","/"})
    public String hello(HttpServletRequest request) {
        logger.info(IpUtil.getIpAddr(request)+"访问页面。");
        return "web/turtle";
    }
}
