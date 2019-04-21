package com.wxc.happyturtle.service;

import com.wxc.happyturtle.entity.Major;
import com.wxc.happyturtle.entity.Person;
import com.wxc.happyturtle.mapper.MajorMapper;
import com.wxc.happyturtle.mapper.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by WXC on 2018/08/11.
 */
@Service
public class PersonService {
    @Autowired
    private PersonMapper personMapper;


    //随机选择对应专业的需要人数
    public List<Person> selectPersonList(Person person){
        return personMapper.selectPersonList(person);
    }

}
