package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.Card;
import com.catalog.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CardMapper
{
    @Select("select card_id from user_follow_card where user_id = #{id}")
    List<Integer> getCardIdsByUserId(int id);

    List<Card> getCardsByIds(List<Integer> ids);

    List<Card> getCardsByKeyWord(String keyWord);

    @Select("select * from card where id = #{id}")
    Card getCardById(int id);

    @AutoFill(OperationType.UPDATE)
    @Update("update card set status = 1 where id = #{id}")
    void acceptCardById(int id);
}
