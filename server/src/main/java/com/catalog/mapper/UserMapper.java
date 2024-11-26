package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.User;
import com.catalog.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper
{
    @Select("select * from user where open_id = #{openId}")
    User getByOpenId(String openId);

    @AutoFill(value = OperationType.INSERT)
    void insert(User user);
}
