package com.catalog.controller.user;

import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.Card;
import com.catalog.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "C端用户卡片相关接口")
@Slf4j
public class CardController
{
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
        return Result.success();
    }
    @GetMapping("/my_card")
    @ApiOperation("获取用户自己的卡片")
    public Result<List<Card>> getUserOwnCard()
    {
        return Result.success();
    }
}