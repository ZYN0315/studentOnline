package com.example.demo.dao;

import com.example.demo.entity.WXInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface WXMapper {
    @Insert("insert into wxinfo (signature,openid,noncestr,timestamp,username)" +
            " values(#{signature},#{openid},#{noncestr},#{timestamp},#{username})")
    public void Insert(@Param("signature") String signature,@Param("openid") String openid,
                       @Param("noncestr") String noncestr,@Param("timestamp") Long timestamp,
                       @Param("username") String username);

    @Select("select * from wxinfo where openid = #{openid}")
    public WXInfo Select(@Param("openid") String openid);

    @Select("select * from wxinfo where id = #{id}")
    public WXInfo SelectById(@Param("id") int id);

    @Update("update wxinfo set signature = #{signature},openid=#{openid},noncestr=#{noncestr},timestamp=#{timestamp}" +
            " where id = #{id}")
    public void Update(WXInfo wxInfo);
}
