package com.catalog.controller.user;

import com.catalog.constant.MessageConstant;
import com.catalog.context.BaseContext;
import com.catalog.dto.CardIdDTO;
import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.result.Result;
import com.catalog.service.CardService;
import com.catalog.service.ImgService;
import com.catalog.service.UserService;
import com.catalog.utils.FileUtil;
import com.catalog.utils.MathUtil;
import com.catalog.vo.CardVO;
import com.fasterxml.jackson.databind.ser.Serializers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "C端用户卡片相关接口")
@Slf4j
public class CardController
{
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ImgService imgService;

    @PostMapping("/home")
    @ApiOperation("首页获取卡片")
    public Result<List<CardVO>> getHomeCard(@RequestBody UserHomeCardDTO userHomeCardDTO)
    {
        List<Card> cards = cardService.getAcceptedCard(userHomeCardDTO);
        if(cards == null || cards.isEmpty()) return Result.success();
        List<CardVO> cardVOs = new ArrayList<>();
        for(Card c : cards)
        {
            List<Img> imgs = cardService.getCardImgs(c);
            double cardDis = MathUtil.getDistance(c.getLongitude(), c.getLatitude(), userHomeCardDTO.getLongitude(), userHomeCardDTO.getLatitude());
            CardVO cardVO = CardVO.builder()
                    .card_id(c.getId())
                    .pet_name(c.getAnimalName())
                    .intro(c.getIntroduction())
                    .follow_num(c.getFollowNum())
                    .location(c.getLocationDescription())
                    .like_num(c.getTotalLikeNum())
                    .img_url(imgService.getImageById(c.getImgId()).getUrl())
                    .follow(userService.isFollowCard(c))
                    .like(userService.isLikeCard(c))
                    .imgs(imgs)
                    .distance(cardDis)
                    .build();
            cardVOs.add(cardVO);
        }
        cardVOs.sort(Comparator.comparingDouble(CardVO::getDistance));
        cardVOs = cardVOs.subList(0, Math.min(cardVOs.size(), userHomeCardDTO.getNum()));
        return Result.success(cardVOs);
    }
    @GetMapping("/card")
    @ApiOperation("根据card_id获取卡片")
    public Result<CardVO> getOneCardById(@RequestParam("card_id") int cardId)
    {
        Card card = cardService.getCardById(cardId);
        if(card == null) return Result.error(MessageConstant.CARD_NOT_EXIST);
        List<Img> imgs = cardService.getCardImgs(card);
        CardVO cardVO = CardVO.builder()
                .card_id(card.getId())
                .pet_name(card.getAnimalName())
                .intro(card.getIntroduction())
                .follow_num(card.getFollowNum())
                .location(card.getLocationDescription())
                .like_num(card.getTotalLikeNum())
                .img_url(imgService.getImageById(card.getImgId()).getUrl())
                .follow(userService.isFollowCard(card))
                .like(userService.isLikeCard(card))
                .imgs(imgs)
                .build();
        return Result.success(cardVO);
    }
    @GetMapping("/cards")
    @ApiOperation("根据关键词获取卡片")
    public Result<List<CardVO>> getCardsByKeyWord(@RequestParam String key_word)
    {
        log.info("User:{} get cards by key:{}", BaseContext.getCurrentId(), key_word);
        List<Card> cards = cardService.getCardsByKeyWord(key_word);
        if(cards == null || cards.isEmpty()) return Result.success();
        List<CardVO> cardVOs = new ArrayList<>();
        for(Card c : cards)
        {
            List<Img> imgs = cardService.getCardImgs(c);
            CardVO cardVO = CardVO.builder()
                    .card_id(c.getId())
                    .pet_name(c.getAnimalName())
                    .intro(c.getIntroduction())
                    .follow_num(c.getFollowNum())
                    .location(c.getLocationDescription())
                    .like_num(c.getTotalLikeNum())
                    .img_url(imgService.getImageById(c.getImgId()).getUrl())
                    .follow(userService.isFollowCard(c))
                    .like(userService.isLikeCard(c))
                    .imgs(imgs)
                    .build();
            cardVOs.add(cardVO);
        }
        return Result.success(cardVOs);
    }
    @GetMapping("/my_card")
    @ApiOperation("获取用户自己的卡片")
    public Result<List<CardVO>> getUserOwnCard()
    {
        int userId = BaseContext.getCurrentId();
        List<Card> cards = userService.getUploadCardsById(userId);
        if(cards == null || cards.isEmpty()) return Result.success();
        List<CardVO> cardVOs = new ArrayList<>();
        for(Card c : cards)
        {
            List<Img> imgs = cardService.getCardImgs(c);
            CardVO cardVO = CardVO.builder()
                    .card_id(c.getId())
                    .pet_name(c.getAnimalName())
                    .intro(c.getIntroduction())
                    .follow_num(c.getFollowNum())
                    .location(c.getLocationDescription())
                    .like_num(c.getTotalLikeNum())
                    .img_url(imgService.getImageById(c.getImgId()).getUrl())
                    .follow(userService.isFollowCard(c))
                    .like(userService.isLikeCard(c))
                    .imgs(imgs)
                    .build();
            cardVOs.add(cardVO);
        }
        return Result.success(cardVOs);
    }
    @PutMapping("/card_like")
    @ApiOperation("用户点赞卡片")
    public Result<String> userLikeCard(@RequestBody CardIdDTO card)
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} like card:{}", userId, card.getCard_id());
        userService.likeCardById(userId, card.getCard_id());
        return Result.success();
    }
    @PutMapping("/card_star")
    @ApiOperation("用户收藏卡片")
    public Result<String> userFollowCard(@RequestBody CardIdDTO card)
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} follow card:{}", userId, card.getCard_id());
        userService.followCardById(userId, card.getCard_id());
        return Result.success();
    }
    @PostMapping(value = "/card", consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation("上传卡片")
    public Result<String> uploadCard(@RequestPart("img") MultipartFile imgFIle, @RequestParam String intro, @RequestParam double longitude, @RequestParam double latitude, @RequestParam String name, @RequestParam String location)
    {
        int userId = BaseContext.getCurrentId();
        Img img = imgService.uploadImgToBed(imgFIle);
        Card card = new Card();
        card.setImgId(img.getId());
        card.setIntroduction(intro);
        card.setAnimalName(name);
        card.setLongitude(longitude);
        card.setLatitude(latitude);
        card.setLocationDescription(location);
        card.setStatus(0);
        cardService.addCard(card);
        userService.uploadCard(userId, card.getId());
        return Result.success(MessageConstant.UPLOAD_SUCCESS);
    }
    @PostMapping(value = "/upload_card_img", consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation("上传卡片图片")
    public Result<String> uploadCardImg(@RequestParam int card_id, @RequestPart("img_file") MultipartFile imgFile)
    {
        int userId = BaseContext.getCurrentId();
        Card card = cardService.getCardById(card_id);
        List<Card> userCards =userService.getUploadCardsById(userId);

        if(!userCards.contains(card))
        {
            return Result.error(MessageConstant.ACCESS_DENIED);
        }
        Img img = imgService.uploadImgToBed(imgFile);
        cardService.addCardImg(card, img);
        return Result.success(img.getUrl());
    }
}
