package com.catalog.service;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MsgService
{
    void updateMsgByIds(List<Integer> ids);

    void sendDeleteMsg(int cardId);

    void sendAcceptMsg(int cardId);
}
