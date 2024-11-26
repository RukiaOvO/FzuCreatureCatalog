package com.catalog.mapper;

import com.catalog.entity.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CardMapper
{
    @Select("select card_id from user_follow_card where user_id = #{id}")
    List<Integer> getCardIdsByUserId(int id);

    List<Card> getCardsByIds(List<Integer> ids);
}
