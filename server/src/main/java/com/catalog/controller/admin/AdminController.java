package com.catalog.controller.admin;

import com.catalog.constant.MessageConstant;
import com.catalog.dto.AdminLoginDTO;
import com.catalog.dto.PageDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.entity.Page;
import com.catalog.result.Result;
import com.catalog.service.*;
import com.catalog.service.Impl.PageServiceImpl;
import com.catalog.vo.AdminLoginVO;
import com.catalog.vo.CardListVO;
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
    @Autowired
    private PageService pageService;

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
    public Result<CardListVO> showHomeCards(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit)
    {
        List<Card> cards = cardService.getUnAcceptedCard();
        if(cards == null || cards.isEmpty()) return Result.success();
        int count = cards.size();
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
        cardVOs = cardVOs.subList(Math.min(cardVOs.size(), offset), Math.min(cardVOs.size(), offset+limit));
        return Result.success(new CardListVO(count, cardVOs));
    }

    @DeleteMapping("/reject_card")
    @ApiOperation("删除卡片")
    public Result<String> deleteRejectCard(@RequestParam(name = "card_id") int cardId)
    {
        log.info("管理员拒绝通过卡片:{}",cardId);
        if(cardService.getCardById(cardId) == null)
        {
            return Result.error(MessageConstant.CARD_NOT_EXIST);
        }
        msgService.sendDeleteMsg(cardId);
        adminService.deleteCard(cardId);
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }
    @PutMapping("/accept_card")
    @ApiOperation("通过卡片审核")
    public Result<String> acceptCard(@RequestParam(name = "card_id") int cardId)
    {
        log.info("管理员允许通过卡片:{}", cardId);
        Card card = cardService.getCardById(cardId);
        if(card == null)
        {
            return Result.error(MessageConstant.CARD_NOT_EXIST);
        }
        if(card.getStatus() == 1)
        {
            return Result.error(MessageConstant.CARD_HAVE_ACCEPTED);
        }
        cardService.acceptCardById(cardId);
        msgService.sendAcceptMsg(cardId);
        return Result.success(MessageConstant.UPDATE_SUCCESS);
    }
    @PostMapping("/page")
    @ApiOperation(("上传官方图鉴"))
    public Result<String> uploadPage(@RequestBody PageDTO pageDTO)
    {
        pageService.uploadPage(pageDTO);
        return Result.success(MessageConstant.UPLOAD_SUCCESS);
    }
}
