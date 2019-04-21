package com.wxc.happyturtle.mapper;

import com.wxc.happyturtle.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Created by WXC on 2018/08/11.
 */
@Mapper
public interface PersonMapper {

    //随机选择对应专业的需要人数
    @Select("select * from person where major_id = #{major_id} order by rand() limit #{size}")
    List<Person> selectPersonList(Person person);


}