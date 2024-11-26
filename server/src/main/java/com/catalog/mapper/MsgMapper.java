package com.catalog.mapper;

import com.catalog.entity.Msg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgMapper
{
    List<Msg> getMsgByIds(List<Integer> ids);

    @Delete("delete from msg where id = #{id}")
    void deleteMsgById(int id);
}
