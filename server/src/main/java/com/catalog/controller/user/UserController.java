package com.catalog.controller.user;

import com.catalog.constant.MessageConstant;
import com.catalog.context.BaseContext;
import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.*;
import com.catalog.mapper.FollowMapper;
import com.catalog.service.CardService;
import com.catalog.service.ImgService;
import com.catalog.service.PageService;
import com.catalog.service.UserService;
import com.catalog.dto.UserLoginDTO;
import com.catalog.result.Result;
import com.catalog.utils.FileUtil;
import com.catalog.utils.MathUtil;
import com.catalog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Api(tags = "C端用户相关接口")
@Slf4j
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private ImgService imgService;
    @Autowired
    private CardService cardService;
    @Autowired
    private PageService pageService;

    @PostMapping("/login")
    @ApiOperation("微信登入")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO)
    {
        log.info("WeChat User Login: code:{}", userLoginDTO.getCode());
        User user = userService.wxLogin(userLoginDTO);
        String userToken = userService.generateToken(user);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenId())
                .token(userToken)
                .build();
        return Result.success(userLoginVO);
    }
    @PostMapping("/logout")
    @ApiOperation("微信登出")
    public Result<String> logout()
    {
        log.info("WeChat User:{} Logout", BaseContext.getCurrentId());
        return Result.success();
    }

    @GetMapping("/follow")
    @ApiOperation("获取关注列表")
    public Result<List<CardVO>> getFollowCard(@RequestParam("sort_rule") int sortRule)
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getFollowCard rule: {}", userId, sortRule);
        List<Card> cards = userService.getFollowCardsById(userId, sortRule);
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
    @GetMapping("/info")
    @ApiOperation("获取用户个人信息")
    public Result<UserInfoVO> getUserOwnInfo()
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getUserInfo.", userId);
        User user = userService.getUserById(userId);
        Img userImage = imgService.getImageById(user.getImgId());

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setNickname(user.getNickname());
        userInfo.setImg_url(userImage == null ? "null" : userImage.getUrl());
        userInfo.setFollow_num(userService.getUserFollowNumById(userId));
        userInfo.setImg_num(userService.getUserImgNumById(userId));
        userInfo.setLike_num(userService.getUserLikeNumById(userId));

        return Result.success(userInfo);
    }
    @PostMapping(value = "/info", consumes = "multipart/form-data", produces = "application/json")
    @ApiOperation("上传用户个人信息")
    public Result<String> uploadUserOwnInfo(@RequestParam("nickname") String nickname, @RequestPart("img") MultipartFile imgFile)
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} uploadUserInfo.", userId);
        Img img = imgService.uploadImgToBed(imgFile);
        userService.updateUserInfo(nickname, img);
        return Result.success(img.getUrl());
    }
    @DeleteMapping("/deletecard")
    @ApiOperation("删除自己的卡片")
    public Result<String> deleteUserOwnCard(@RequestParam("card_id") int cardId)
    {
        Card card = cardService.getCardById(cardId);
        if(card == null || !userService.deleteUserOwnCard(cardId))
        {
            return Result.error(MessageConstant.CARD_NOT_EXIST);
        }
        return Result.success(MessageConstant.DELETE_SUCCESS);
    }

    @GetMapping("/page")
    @ApiOperation("根据id获取单个官方图鉴")
    public Result<PageVO> getOnePageById(@RequestParam("page_id") int pageId)
    {
        Page page = pageService.getOnePageById(pageId);
        if(page == null)
        {
            return Result.error(MessageConstant.PAGE_NOT_EXIST);
        }
        PageVO pageVO = PageVO.builder()
                .id(page.getId())
                .description(page.getDescription())
                .position(page.getPosition())
                .imgUrl(page.getImgUrl())
                .nickname(page.getNickname())
                .content(page.getContent()).build();
        return Result.success(pageVO);
    }
    @GetMapping("/pagelist")
    @ApiOperation("获取官方图鉴列表")
    public Result<PageListVO> getPageList(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit)
    {
        List<Page> pages = pageService.getPages();
        if(pages == null || pages.isEmpty()) return Result.success();
        int count = pages.size();
        List<PageThumbnailVO> pageVOs = new ArrayList<>();
        for(Page p : pages)
        {
            PageThumbnailVO pageVO = PageThumbnailVO.builder()
                    .id(p.getId())
                    .nickname((p.getNickname()))
                    .position(p.getPosition())
                    .description(p.getDescription())
                    .imgUrl(p.getImgUrl())
                    .build();
            pageVOs.add(pageVO);
        }
        return Result.success(new PageListVO(count, pageVOs));
    }
}
