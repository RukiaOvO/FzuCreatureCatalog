package com.catalog.service;

import com.catalog.dto.UserLoginDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Msg;
import com.catalog.entity.User;
import com.catalog.vo.UserInfoVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserService
{
    User wxLogin(UserLoginDTO userLoginDTO);

    String generateToken(User user);

    User getUserById(int id);

    int getUserFollowNumById(int id);

    int getUserLikeNumById(int id);

    int getUserImgNumById(int id);

    List<Card> getFollowCardsById(int userId, int sortRule);

    List<Card> getUploadCardsById(int id);

    List<Msg> getUserMsgById(int id);

    void deleteUserMsgByMsgId(int id);

    List<Integer> getMsgIdsById(int id);

    Integer getMsgIdById(int id);

    void likeCardById(int userId, int cardId);

    void followCardById(int userId, int cardId);
}
