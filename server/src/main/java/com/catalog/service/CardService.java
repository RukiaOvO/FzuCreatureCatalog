package com.catalog.service;

import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.vo.CardVO;

import java.util.List;

public interface CardService
{
    Card getCardById(int id);

    List<Card> getCardsByKeyWord(String keyWord);

    void acceptCardById(int cardId);

    void addCardImg(Card card, Img img);

    void updateDailyData();

    List<Card> getTotalRankList();

    List<Card> getDailyRankList();

    int addCard(Card card);

    List<Img> getCardImgs(Card card);

    List<Card> getAcceptedCard(UserHomeCardDTO userHomeCardDTO);

    List<Card> getUnAcceptedCard();
}
