package com.wxc.happyturtle.service;

import com.wxc.happyturtle.entity.Major;
import com.wxc.happyturtle.mapper.MajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by WXC on 2018/08/11.
 */
@Service
public class MajorService {
    @Autowired
    private MajorMapper majorMapper;


    //查询专业列表
    public List<Major> selectMajorList(){
        return majorMapper.selectMajorList();
    }

    //根据ID查询专业
    public Major selectMajorById(int majorId){
        return majorMapper.selectMajorById(majorId);
    }

}
