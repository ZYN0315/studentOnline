package com.example.demo.dao;

import com.example.demo.entity.Anser;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface AnserMapper {

    @Insert("insert into anser (type,ansername,askerid,status,openid,imgurl)" +
            " values (#{type},#{ansername},#{askerid},#{status},#{openid},#{imgurl})")
    public void Insert(Anser anser);

    @Select("select * from anser where askerid = #{askerid}")
    List<Anser> getResults(@Param("askerid") String askerid);

    @Select("select * from anser where id = #{id}")
    Anser anser(@Param("id") int id);

    @Select("select * from anser where askerid = #{askerid} && openid = #{openid}")
    public Anser getAnser(@Param("askerid") String askerid,@Param("openid") String openid);

    @Select("select id from anser where askerid = #{askerid} && openid = #{openid}")
    public int getId(@Param("askerid") String askerid, @Param("openid") String openid);

    @Update("update anser set result=#{result},status=#{status},matchornot=#{matchornot}" +
            " where openid=#{openid} && askerid=#{askerid}")
    public void Update(Anser anser);

}
