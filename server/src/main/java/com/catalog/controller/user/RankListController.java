package com.catalog.controller.user;


import com.catalog.context.BaseContext;
import com.catalog.entity.Card;
import com.catalog.result.Result;
import com.catalog.service.CardService;
import com.catalog.service.ImgService;
import com.catalog.service.UserService;
import com.catalog.vo.RankCardVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ranklist")
@Api(tags = "C端用户排行榜相关接口")
@Slf4j
public class RankListController
{
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImgService imgService;

    @GetMapping("/total")
    @ApiOperation("获取排行榜总榜")
    public Result<List<RankCardVO>> getTotalRankList()
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getTotalRankList", userId);
        List<Card> cards = cardService.getTotalRankList();
        if(cards.isEmpty()) return Result.success();
        List<RankCardVO> cardVOs = new ArrayList<>();
        for(Card c : cards)
        {
            RankCardVO newCardVO = RankCardVO.builder()
                    .card_id(c.getId())
                    .pet_name(c.getAnimalName())
                    .intro(c.getIntroduction())
                    .follow_num(c.getFollowNum())
                    .location(c.getLocationDescription())
                    .like_num(c.getTotalLikeNum())
                    .img_url(imgService.getImageById(c.getImgId()).getUrl())
                    .is_follow(userService.isFollowCard(c))
                    .is_like(userService.isLikeCard(c))
                    .build();
            cardVOs.add(newCardVO);
        }
        return Result.success(cardVOs);
    }
    @GetMapping("/daily")
    @ApiOperation("获取每日排行榜")
    public Result<List<RankCardVO>> getDailyRankList()
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getDailyRankList", userId);
        List<Card> cards = cardService.getDailyRankList();
        if(cards.isEmpty()) return Result.success();
        List<RankCardVO> cardVOs = new ArrayList<>();
        for(Card c : cards)
        {
            RankCardVO newCardVO = RankCardVO.builder()
                    .card_id(c.getId())
                    .pet_name(c.getAnimalName())
                    .intro(c.getIntroduction())
                    .follow_num(c.getFollowNum())
                    .location(c.getLocationDescription())
                    .like_num(c.getTotalLikeNum())
                    .img_url(imgService.getImageById(c.getImgId()).getUrl())
                    .is_follow(userService.isFollowCard(c))
                    .is_like(userService.isLikeCard(c))
                    .build();
            cardVOs.add(newCardVO);
        }
        return Result.success(cardVOs);
    }

}
