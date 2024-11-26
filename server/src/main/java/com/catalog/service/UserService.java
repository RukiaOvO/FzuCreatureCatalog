package com.catalog.service;

import com.catalog.dto.UserLoginDTO;
import com.catalog.entity.Card;
import com.catalog.entity.User;
import com.catalog.vo.UserInfoVO;

import java.util.List;

public interface UserService
{
    User wxLogin(UserLoginDTO userLoginDTO);

    String generateToken(User user);

    User getUserById(int id);

    int getUserFollowNumById(int id);

    int getUserLikeNumById(int id);

    int getUserImgNumById(int id);

    List<Card> getFollowCardsById(int id);

    List<Card> getUploadCardsById(int id);
}
