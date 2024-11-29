package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.User;
import com.catalog.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper
{
    @Select("select * from user where open_id = #{openId}")
    User getUserByOpenId(String openId);

    @AutoFill(value = OperationType.INSERT)
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User getUserById(int id);

    @Select("select msg_id from user_msg where user_id = #{id}")
    List<Integer> getMsgIdsById(int id);

    @Delete("delete from user_msg where msg_id = #{id}")
    void deleteMsgByMsgId(int id);

    @Select("select * from user_msg where user_id = #{id}")
    int getMsgIdById(int id);

    void addUserImg(int userId, int imgId);

    @Select("select card_id from user_like_card where user_id = #{id}")
    List<Integer> getLikeCardIdsById(int id);

    @Insert("insert into user_like_card(card_id, user_id, create_time, update_time) values(#{card_id}, #{user_id}, NOW(), NOW())")
    void likeCard(int user_id, int card_id);

    @Delete("delete from user_like_card where user_id = #{user_id} and card_id = #{card_id}")
    void dislikeCard(int user_id, int card_id);

    @Select("select card_id from user_follow_card where user_id = #{id}")
    List<Integer> getFollowCardIdsById(int id);

    @Insert("insert into user_follow_card(card_id, user_id, create_time, update_time) values(#{card_id}, #{user_id}, NOW(), NOW())")
    void followCard(int user_id, int card_id);

    @Delete("delete from user_follow_card where user_id = #{user_id} and card_id = #{card_id}")
    void unfollowCard(int user_id, int card_id);

    @Update("update user set nickname = #{nickname}, img_id = #{img_id}, update_time = NOW() where id = #{user_id}")
    void updateUserInfo(int user_id, String nickname, int img_id);

    @Select("select user_id from user_upload_card where card_id = #{cardId}")
    int getUserByCardId(int cardId);
}
