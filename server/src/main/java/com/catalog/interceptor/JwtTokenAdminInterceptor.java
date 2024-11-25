package com.catalog.interceptor;

import com.catalog.constant.JwtClaimsConstant;
import com.catalog.properties.JwtProperties;
import com.catalog.properties.WeChatProperties;
import com.catalog.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor
{
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private WeChatProperties weChatProperties;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }

        String token = request.getHeader(jwtProperties.getAdminTokenName());

        log.info("管理员jwt校验:{}", token);
        Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
        token = claims.get(JwtClaimsConstant.APP_SECRET).toString();
        log.info("secret校验：{}", token);

        if(token.equals(weChatProperties.getSecret()))
        {
            return true;
        }
        else
        {
            response.setStatus(401);
            return false;
        }
    }
}
