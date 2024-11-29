package com.catalog.service.Impl;

import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.mapper.CardMapper;
import com.catalog.mapper.ImgMapper;
import com.catalog.mapper.UploadMapper;
import com.catalog.service.CardService;
import com.catalog.vo.CardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardServiceImpl implements CardService
{
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private UploadMapper uploadMapper;
    @Autowired
    private ImgMapper imgMapper;

    @Override
    public Card getCardById(int id)
    {
        return cardMapper.getCardById(id);
    }

    @Override
    public List<Card> getCardsByKeyWord(String keyWord)
    {
        return cardMapper.getCardsByKeyWord(keyWord);
    }

    @Override
    public void acceptCardById(int cardId)
    {
        cardMapper.acceptCardById(cardId);
    }

    @Override
    public void addCardImg(Card card, Img img)
    {
        cardMapper.addCardImg(card.getId(), img.getId());
    }

    @Scheduled(cron = "0 0 0 * * ?") // 每日零点执行
    @Transactional
    @Override
    public void updateDailyData()
    {
        List<Card> cards = cardMapper.getAllCards();
        cardMapper.updateDailyLike(cards);
    }

    @Override
    public List<Card> getTotalRankList()
    {
        return cardMapper.getTotalRankList();
    }

    @Override
    public List<Card> getDailyRankList()
    {
        return cardMapper.getDailyRankList();
    }

    @Override
    public int addCard(Card card)
    {
        return cardMapper.addCard(card);
    }

    @Transactional
    @Override
    public List<Img> getCardImgs(Card card)
    {
        List<Integer> ids = cardMapper.getCardImgIdsByCardId(card.getId());
        if(ids == null || ids.isEmpty()) return null;
        return imgMapper.getImagesByIds(ids);
    }

    @Override
    public List<Card> getAcceptedCard(UserHomeCardDTO userHomeCardDTO)
    {
        return cardMapper.getAcceptedCards();
    }

}
