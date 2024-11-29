package com.catalog.mapper;

import com.catalog.entity.Msg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgMapper
{
    List<Msg> getMsgByIds(List<Integer> ids);

    @Delete("delete from msg where id = #{id}")
    void deleteMsgById(int id);

    void updateMsgByIds(List<Integer> ids);

    int insert(Msg msg);

    @Insert("insert into user_msg(user_id, msg_id, create_time, update_time) values (#{userId}, #{msgId}, NOW(), NOW())")
    void sendMsg(int userId, int msgId);
}
