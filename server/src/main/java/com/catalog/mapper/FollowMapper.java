package com.catalog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FollowMapper
{
    @Select("select count(*) from user_follow_card where user_id = #{id}")
    int getFollowNumByUserId(int id);
}
