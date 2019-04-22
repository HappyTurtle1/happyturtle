package com.wxc.happyturtle.controller;

import com.wxc.happyturtle.api.ApiResponse;
import com.wxc.happyturtle.entity.Major;
import com.wxc.happyturtle.entity.Person;
import com.wxc.happyturtle.service.MajorService;
import com.wxc.happyturtle.service.PersonService;
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

    @GetMapping("/personSelect")
    public String personSelect(ModelMap modelMap) {


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
