package com.catalog.service;

import com.catalog.dto.UserLoginDTO;
import com.catalog.entity.User;

public interface UserService
{
    User wxLogin(UserLoginDTO userLoginDTO);

    String generateToken(User user);
}
