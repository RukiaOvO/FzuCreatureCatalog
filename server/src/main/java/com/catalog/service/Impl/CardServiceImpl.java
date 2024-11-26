package com.catalog.service.Impl;

import com.catalog.entity.Card;
import com.catalog.mapper.CardMapper;
import com.catalog.mapper.UploadMapper;
import com.catalog.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService
{
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UploadMapper uploadMapper;
    @Override
    public Card getCardById(int id)
    {
        return cardMapper.getCardById(id);
    }

}
