package com.example.demo.dao;

import com.example.demo.entity.Asker;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface AskerMapper {

    @Select("select * from asker where openid = #{openid}")
    List<Asker> getAskerInfo(@Param("openid") String openid);

    @Insert("insert into asker (username,type,Q1,Q2,Q3,Q4,Q5,Q6,Q7,action1,action2,action3,level,openid) " +
            "values (#{username},#{type},#{Q1},#{Q2},#{Q3},#{Q4},#{Q5},#{Q6},#{Q7},#{action1},#{action2},#{action3},#{level},#{openid})")
    void Insert(Asker asker);


}
