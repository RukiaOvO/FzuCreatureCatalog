package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.Card;
import com.catalog.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CardMapper
{
    @Select("select card_id from user_follow_card where user_id = #{id}")
    List<Integer> getCardIdsByUserId(int id);

    List<Card> getCardsByIds(int ids);

    @Select("select * from card where status = 1")
    List<Card> getAcceptedCards();

    List<Card> getAcceptedCardsByIds(List<Integer> ids);

    List<Card> getCardsByKeyWord(String keyWord);

    @Select("select * from card where id = #{id}")
    Card getCardById(int id);

    @Update("update card set status = 1, update_time = NOW() where id = #{id}")
    void acceptCardById(int id);

    @Delete("delete from card where id = #{id}")
    void deleteCardById(int id);

    @Select("select * from card")
    List<Card> getAllCards();

    @Insert("insert into card_img(card_id, picture_id, create_time, update_time) values (#{card_id}, #{img_id}, NOW(), NOW())")
    void addCardImg(int card_id, int img_id);

    void updateDailyLike(List<Card> cards);

    List<Card> getTotalRankList();

    List<Card> getDailyRankList();

    @AutoFill(OperationType.INSERT)
    int addCard(Card card);

    @Update("update card set follow_num = follow_num - 1 where id = #{cardId}")
    void unFollow(int cardId);

    @Update("update card set follow_num = follow_num + 1 where id = #{cardId}")
    void follow(int cardId);

    @Update("update card set daily_like_num = daily_like_num - 1, total_like_num = total_like_num - 1 where id = #{cardId}")
    void disLike(int cardId);

    @Update("update card set daily_like_num = daily_like_num + 1, total_like_num = total_like_num + 1 where id = #{cardId}")
    void like(int cardId);

    @Select("select picture_id from card_img where card_id = #{cardId}")
    List<Integer> getCardImgIdsByCardId(int cardId);

    @Select("select * from card where status = 0")
    List<Card> getUnAcceptedCard();
}
