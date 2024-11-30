package com.catalog.mapper;

import com.catalog.entity.Card;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UploadMapper
{
    @Select("select card_id from user_upload_card where user_id = #{id}")
    List<Integer> getCardIdsByUserId(int id);

    @Insert("insert into user_upload_card(user_id, card_id, create_time, update_time) values(#{userId}, #{cardId}, NOW(), NOW())")
    void userUploadCard(int userId, int cardId);
}
