package com.catalog.controller.admin;

import com.catalog.constant.MessageConstant;
import com.catalog.dto.AdminLoginDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.result.Result;
import com.catalog.service.AdminService;
import com.catalog.service.CardService;
import com.catalog.service.ImgService;
import com.catalog.service.MsgService;
import com.catalog.utils.MathUtil;
import com.catalog.vo.AdminLoginVO;
import com.catalog.vo.CardVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
@Slf4j
public class AdminController
{
    @Autowired
    private AdminService adminService;
    @Autowired
    private CardService cardService;
    @Autowired
    private MsgService msgService;
    @Autowired
    private ImgService imgService;

    @PostMapping("/login")
    @ApiOperation("管理员登入")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO)
    {
        Boolean isAdmin = adminService.login(adminLoginDTO);
        if(isAdmin)
        {
            AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                    .token(adminService.getAccessToken())
                    .build();
            return Result.success(adminLoginVO);
        }
        else
        {
            return Result.error(MessageConstant.LOGIN_FAILED);
        }
    }
    @PostMapping("/logout")
    @ApiOperation("管理员登出")
    public Result<String> logout()
    {
        return Result.success();
    }

    @GetMapping("/home")
    @ApiOperation("显示主页卡片")
    public Result<List<CardVO>> showHomeCards()
    {
        List<Card> cards = adminService.showHomeCards();
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
                    .imgs(imgs)
                    .build();
            cardVOs.add(cardVO);
        }
        return Result.success(cardVOs);
    }

    @DeleteMapping("/reject_card")
    @ApiOperation("删除卡片")
    public Result<String> deleteRejectCard(@RequestParam(name = "card_id") int cardId)
    {
        log.info("管理员拒绝通过卡片:{}",cardId);
        adminService.deleteCard(cardId);
        msgService.sendDeleteMsg(cardId);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }
    @PutMapping("/accept_card")
    @ApiOperation("通过卡片审核")
    public Result<String> acceptCard(@RequestParam(name = "card_id") int cardId)
    {
        log.info("管理员允许通过卡片:{}", cardId);
        cardService.acceptCardById(cardId);
        msgService.sendAcceptMsg(cardId);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }
}
