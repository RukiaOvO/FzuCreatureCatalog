package com.catalog.service.Impl;

import com.catalog.entity.Card;
import com.catalog.mapper.CardMapper;
import com.catalog.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService
{
    @Autowired
    private CardMapper cardMapper;
    @Override
    public Card getCardById(int id)
    {
        return cardMapper.getCardById(id);
    }
}
