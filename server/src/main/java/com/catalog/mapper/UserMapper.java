package com.catalog.mapper;

import com.catalog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper
{
    @Select("select * from user where openid = #{openId}")
    User getByOpenId(String openId);

    void insert(User user);
}
