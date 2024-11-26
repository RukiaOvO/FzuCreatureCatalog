package com.catalog.service.Impl;

import com.catalog.constant.JwtClaimsConstant;
import com.catalog.dto.AdminLoginDTO;
import com.catalog.dto.RejectCardDTO;
import com.catalog.entity.Card;
import com.catalog.mapper.AdminMapper;
import com.catalog.properties.JwtProperties;
import com.catalog.properties.WeChatProperties;
import com.catalog.service.AdminService;
import com.catalog.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService
{
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Boolean login(AdminLoginDTO adminLoginDTO)
    {
        return (jwtProperties.getAdminName().equals(adminLoginDTO.getUsername())
                && jwtProperties.getAdminPassword().equals(adminLoginDTO.getPassword()));
    }

    @Override
    public String getAccessToken()
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.APP_SECRET, weChatProperties.getSecret());
        return JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);
    }

    @Override
    public void deleteCard(RejectCardDTO rejectCardDTO)
    {
        adminMapper.deleteCardById(rejectCardDTO.getCardId());
    }

    @Override
    public List<Card> showHomeCards()
    {
        return adminMapper.selectAllCards();
    }
}
