package com.catalog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface LikeMapper
{
    @Select("select count(*) from user_like_card where user_id = #{id}")
    int getLikeNumByUserId(int id);
}
