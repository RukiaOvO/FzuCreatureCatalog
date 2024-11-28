package com.catalog.controller.user;


import com.catalog.context.BaseContext;
import com.catalog.entity.Card;
import com.catalog.result.Result;
import com.catalog.service.CardService;
import com.catalog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/ranklist")
@Api(tags = "C端用户排行榜相关接口")
@Slf4j
public class RankListController
{
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;

    @GetMapping("/total")
    @ApiOperation("获取排行榜总榜")
    public Result<List<Card>> getTotalRankList()
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getTotalRankList", userId);
        List<Card> cards = cardService.getTotalRankList();
        return Result.success(cards);
    }
    @GetMapping("/daily")
    @ApiOperation("获取每日排行榜")
    public Result<List<Card>> getDailyRankList()
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getDailyRankList", userId);
        List<Card> cards = cardService.getDailyRankList();
        return Result.success(cards);
    }

}
