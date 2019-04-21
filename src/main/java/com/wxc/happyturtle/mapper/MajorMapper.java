package com.wxc.happyturtle.mapper;

import com.wxc.happyturtle.entity.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by WXC on 2018/08/11.
 */
@Mapper
public interface MajorMapper {

    //查询专业列表
    @Select("select * from major where status = 0")
    List<Major> selectMajorList();

    //根据ID查询专业
    @Select("select * from major where id = #{majorId}")
    Major selectMajorById(int majorId);
}