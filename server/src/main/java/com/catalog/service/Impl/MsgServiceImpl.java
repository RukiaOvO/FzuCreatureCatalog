package com.catalog.service.Impl;

import com.catalog.constant.MessageConstant;
import com.catalog.entity.Msg;
import com.catalog.mapper.CardMapper;
import com.catalog.mapper.MsgMapper;
import com.catalog.mapper.UserMapper;
import com.catalog.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService
{
    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CardMapper cardMapper;

    @Override
    public void updateMsgByIds(List<Integer> ids)
    {
        msgMapper.updateMsgByIds(ids);
    }

    @Transactional
    @Override
    public void sendDeleteMsg(int cardId)
    {
        int userId = userMapper.getUserByCardId(cardId);
        Msg deleteMsg = Msg.builder()
                .content(MessageConstant.REVIEW_FAILED)
                .build();
        msgMapper.insert(deleteMsg);
        msgMapper.sendMsg(userId, deleteMsg.getId());
    }

    @Transactional
    @Override
    public void sendAcceptMsg(int cardId)
    {
        int userId = userMapper.getUserByCardId(cardId);
        Msg deleteMsg = Msg.builder()
                .content(MessageConstant.REVIEW_ACCEPT)
                .build();
        msgMapper.insert(deleteMsg);
        msgMapper.sendMsg(userId, deleteMsg.getId());
    }
}
