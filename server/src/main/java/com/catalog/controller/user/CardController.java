package com.catalog.controller.user;

import com.catalog.constant.MessageConstant;
import com.catalog.context.BaseContext;
import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.result.Result;
import com.catalog.service.CardService;
import com.catalog.service.ImgService;
import com.catalog.service.UserService;
import com.catalog.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

    @GetMapping("/home")
    @ApiOperation("首页获取卡片")
    public Result<List<Card>> getHomeCard(@RequestBody UserHomeCardDTO userHomeCardDTO)
    {
        return Result.success();
    }
    @GetMapping("/card")
    @ApiOperation("根据card_id获取卡片")
    public Result<Card> getOneCardById(@RequestParam int cardId)
    {
        Card card = cardService.getCardById(cardId);
        return Result.success(card);
    }
    @GetMapping("/cards")
    @ApiOperation("根据关键词获取卡片")
    public Result<List<Card>> getCardsByKeyWord(@RequestParam String key_word)
    {
        List<Card> cards = cardService.getCardsByKeyWord(key_word);
        return Result.success(cards);
    }
    @GetMapping("/my_card")
    @ApiOperation("获取用户自己的卡片")
    public Result<List<Card>> getUserOwnCard()
    {
        int userId = BaseContext.getCurrentId();
        List<Card> cards = userService.getUploadCardsById(userId);
        return Result.success(cards);
    }
    @PutMapping("/card_like")
    @ApiOperation("用户点赞卡片")
    public Result<String> userLikeCard(@RequestParam int card_id)
    {
        int userId = BaseContext.getCurrentId();
        userService.likeCardById(userId, card_id);
        return Result.success();
    }
    @PutMapping("/card_star")
    @ApiOperation("用户收藏卡片")
    public Result<String> userFollowCard(@RequestParam int card_id)
    {
        int userId = BaseContext.getCurrentId();
        userService.followCardById(userId, card_id);
        return Result.success();
    }
    @PostMapping(value = "/card", consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation("上传卡片")
    public Result<String> uploadCard(@RequestPart("img") MultipartFile imgFIle, @RequestParam String intro, @RequestParam double longitude, @RequestParam double latitude, @RequestParam String name)
    {
        int userId = BaseContext.getCurrentId();
        Img img = imgService.uploadImgToBed(imgFIle);
        return Result.success();
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
