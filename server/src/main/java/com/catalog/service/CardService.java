package com.catalog.service;

import com.catalog.entity.Card;
import com.catalog.entity.Img;

import java.util.List;

public interface CardService
{
    Card getCardById(int id);

    List<Card> getCardsByKeyWord(String keyWord);

    void acceptCardById(int cardId);

    void addCardImg(Card card, Img img);
}
