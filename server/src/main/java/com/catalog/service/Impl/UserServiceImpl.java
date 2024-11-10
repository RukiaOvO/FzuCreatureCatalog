package com.catalog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catalog.exception.LoginFailedException;
import com.catalog.mapper.UserMapper;
import com.catalog.properties.WeChatProperties;
import com.catalog.service.UserService;
import com.catalog.properties.JwtProperties;
import com.catalog.entity.User;
import com.catalog.dto.UserLoginDTO;
import com.catalog.constant.MessageConstant;
import com.catalog.utils.HttpClientUtil;
import com.catalog.utils.JwtUtil;
import com.catalog.constant.JwtClaimsConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private WeChatProperties weChatProperties;

    @Override
    public User wxLogin(UserLoginDTO userLoginDTO)
    {
        String openid = getOpenid(userLoginDTO.getCode());
        if(openid == null || openid.isEmpty())
        {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        User user = userMapper.getByOpenId(openid);
        if(user == null)
        {
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        return user;
    }

    private String getOpenid(String code)
    {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");

        String json = HttpClientUtil.doGet(weChatProperties.getLoginUrl(), map, null);
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }

    @Override
    public String generateToken(User user)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        return JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
    }
}
