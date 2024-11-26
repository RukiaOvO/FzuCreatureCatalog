package com.catalog.aspect;

import com.catalog.annotation.AutoFill;
import com.catalog.constant.AutoFillConstant;
import com.catalog.context.BaseContext;
import com.catalog.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Component
@Slf4j
@Aspect
public class AutoFillAspect
{
    @Pointcut("execution(* com.catalog.mapper.*.*(..)) && @annotation(com.catalog.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充...");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0)
        {
            return;
        }
        Object entity = args[0];

        LocalDateTime nowTime = LocalDateTime.now();
//        Long currentId = BaseContext.getCurrentId();
        if(operationType == OperationType.INSERT)
        {
            try
            {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
//                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
//                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setCreateTime.invoke(entity, nowTime);
                setUpdateTime.invoke(entity, nowTime);
//                setCreateUser.invoke(entity, currentId);
//                setUpdateUser.invoke(entity, currentId);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(operationType == OperationType.UPDATE)
        {
            try
            {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
//                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                setUpdateTime.invoke(entity, nowTime);
//                setUpdateUser.invoke(entity, currentId);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
