package com.catalog.interceptor;

import com.catalog.constant.JwtClaimsConstant;
import com.catalog.context.BaseContext;
import com.catalog.properties.JwtProperties;
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

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }

        String token = request.getHeader(jwtProperties.getAdminTokenName());

        try
        {
            log.info("管理员jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.ADMIN_ID).toString());
            log.info("当前管理员id：{}", empId);
            BaseContext.setCurrentId(empId);
            return true;
        }
        catch (Exception ex)
        {
            response.setStatus(401);
            return false;
        }
    }
}
