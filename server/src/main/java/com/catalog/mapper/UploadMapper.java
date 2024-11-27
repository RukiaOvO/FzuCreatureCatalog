package com.catalog.mapper;

import com.catalog.entity.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UploadMapper
{
    @Select("select card_id from user_upload_card where user_id = #{id}")
    List<Integer> getCardIdsByUserId(int id);

    @Select("select card_id from user_upload_card where user_id = #{id} order by create_time desc")
    List<Integer> getUploadSortedCardIdsByUserId(int id);
}
