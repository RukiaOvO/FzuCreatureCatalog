package com.catalog.service;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgService
{
    void updateMsgByIds(List<Integer> ids);

    void sendDeleteMsg(int cardId);

    void sendAcceptMsg(int cardId);
}
