package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.User;
import com.catalog.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper
{
    @Select("select * from user where open_id = #{openId}")
    User getUserByOpenId(String openId);

    @AutoFill(value = OperationType.INSERT)
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User getUserById(int id);

    @Select("select msg_id from user_msg where user_id = #{id}")
    List<Integer> getMsgIdsById(int id);

    @Delete("delete from user_msg where msg_id = #{id}")
    void deleteMsgByMsgId(int id);

    @Select("select * from user_msg where user_id = #{id}")
    int getMsgIdById(int id);

    void addUserImg(int userId, int imgId);
}
