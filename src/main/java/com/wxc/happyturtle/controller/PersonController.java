package com.wxc.happyturtle.controller;

import com.wxc.happyturtle.Constants;
import com.wxc.happyturtle.api.ApiResponse;
import com.wxc.happyturtle.entity.LoginUser;
import com.wxc.happyturtle.entity.Major;
import com.wxc.happyturtle.entity.Person;
import com.wxc.happyturtle.entity.UserSession;
import com.wxc.happyturtle.service.LoginUserService;
import com.wxc.happyturtle.service.MajorService;
import com.wxc.happyturtle.service.PersonService;
import com.wxc.happyturtle.util.IpUtil;
import com.wxc.happyturtle.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private MajorService majorService;
    @Autowired
    private PersonService personService;
    @Autowired
    private LoginUserService loginUserService;

    //进入抽取系统登录界面
    @GetMapping("/person/login")
    public String toLogin(HttpServletRequest request) {

        UserSession userSession = (UserSession) request.getSession().getAttribute("user");

        if(userSession != null){
            return "redirect:/personSelect";
        }

        logger.info(IpUtil.getIpAddr(request)+"访问抽取系统。");
        return "web/login";
    }

    //用户登录
    @PostMapping("/person/login")
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


    //人员抽取
    @GetMapping("/personSelect")
    public String personSelect(HttpServletRequest request,ModelMap modelMap) {

        UserSession userSession = (UserSession) request.getSession().getAttribute("user");

        if(userSession == null){
            return "redirect:/person/login";
        }

        List<Major> majorList = majorService.selectMajorList();

        modelMap.put("majorList",majorList);


        return "web/person";
    }


    //抽取选择结果
    @PostMapping("/personSelect")
    @ResponseBody
    public ApiResponse personSelect(HttpServletRequest request) {

        ApiResponse result = new ApiResponse();

        Integer major1 = StringUtil.parseIntegerParam(request.getParameter("major1"));
        Integer count1 = StringUtil.parseIntegerParam(request.getParameter("count1"));

        //抽取专业数量
        Integer count = StringUtil.parseIntegerParam(request.getParameter("count"));

        if(count == null || major1 == null || count1 == null || major1 <= 0 || count1 <= 0){
            logger.warn("参数错误！");
            result.setCode(1);
            result.setMessage("参数错误！");
            return result;
        }

        Person person = new Person();
        person.setMajor_id(major1);
        person.setSize(count1);

        //提示信息（显示哪个专业人员不足）
        String message = "";

        //抽取第一个专业人员
        List<Person> personList = personService.selectPersonList(person);

        if(personList != null && personList.size() < count1){
            Major major = majorService.selectMajorById(major1);
            message = major.getMajor_name()+"只有"+personList.size()+"人。";
        }

        //抽取选择的专业人员
        if(count > 1){
            for (int i=2;i<=count;i++){
                //添加的专业抽取
                Integer majorN = StringUtil.parseIntegerParam(request.getParameter("major"+i));
                Integer countN = StringUtil.parseIntegerParam(request.getParameter("count"+i));

                if(majorN != null && majorN > 0 && countN > 0){
                    person.setMajor_id(majorN);
                    person.setSize(countN);

                    //抽取第二个专业人员
                    List<Person> pList = personService.selectPersonList(person);

                    for (Person person1 : pList){
                        personList.add(person1);
                    }

                    if(pList != null && pList.size() < countN){
                        Major major = majorService.selectMajorById(majorN);
                        message = message + major.getMajor_name()+"只有"+pList.size()+"人。";
                    }
                }
            }
        }

        //获取专业名称
        for(Person myPerson : personList){
            Major major = majorService.selectMajorById(myPerson.getMajor_id());
            myPerson.setMajor_name(major.getMajor_name());
        }

        result.setCode(0);
        result.setData(personList);
        result.setMessage(message);
        return result;
    }
}
