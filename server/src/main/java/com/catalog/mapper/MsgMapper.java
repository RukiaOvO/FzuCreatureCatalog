package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.Msg;
import com.catalog.enumeration.OperationType;
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

    @AutoFill(OperationType.INSERT)
    int insert(Msg msg);
}
