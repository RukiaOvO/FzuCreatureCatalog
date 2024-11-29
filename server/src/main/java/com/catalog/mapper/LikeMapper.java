package com.catalog.mapper;

import com.catalog.entity.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface LikeMapper
{
    @Select("select count(*) from user_like_card where user_id = #{id}")
    int getLikeNumByUserId(int id);

    @Select("select * from user_like_card where user_id = #{userId} and card_id = #{cardId}")
    Card getLikeCard(int userId, int cardId);
}
