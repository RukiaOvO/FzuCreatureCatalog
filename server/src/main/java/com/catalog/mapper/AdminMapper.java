package com.catalog.mapper;

import com.catalog.entity.Card;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper

public interface AdminMapper
{
    @Select("select * from card")
    List<Card> selectAllCards();

    @Delete("delete from card where id = #{id}")
    void deleteCardById(int id);

}
