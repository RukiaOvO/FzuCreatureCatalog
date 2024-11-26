package com.catalog.service;

import com.catalog.entity.Card;

import java.util.List;

public interface CardService
{
    Card getCardById(int id);

    List<Card> getCardsByKeyWord(String keyWord);

}
