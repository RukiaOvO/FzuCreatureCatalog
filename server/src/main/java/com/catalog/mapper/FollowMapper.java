package com.catalog.mapper;

import com.catalog.entity.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper
{
    @Select("select count(*) from user_follow_card where user_id = #{id}")
    int getFollowNumByUserId(int id);

    @Select("select card_id from user_follow_card where user_id = #{id}")
    List<Integer> getCardIdsByUserId(int id);

    @Select("select card_id from user_follow_card where user_id = #{id} order by create_time desc")
    List<Integer> getFollowSortedCardIdsByUserId(int id);

    @Select("select * from user_follow_card where user_id = #{userId} and card_id = #{cardId}")
    Card getFollowCard(int userId, int cardId);
}
