package com.catalog.service;

import com.catalog.dto.UserLoginDTO;
import com.catalog.entity.User;
import com.catalog.vo.UserInfoVO;

public interface UserService
{
    User wxLogin(UserLoginDTO userLoginDTO);

    String generateToken(User user);

    User getUserById(int id);

    int getUserFollowNumById(int id);

    int getUserLikeNumById(int id);

    int getUserImgNumById(int id);
}
