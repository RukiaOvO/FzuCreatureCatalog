package com.catalog.service.Impl;

import com.catalog.mapper.MsgMapper;
import com.catalog.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsgServiceImpl implements MsgService
{
    @Autowired
    private MsgMapper msgMapper;
    @Override
    public void updateMsgByIds(List<Integer> ids)
    {
            msgMapper.updateMsgByIds(ids);
    }
}
