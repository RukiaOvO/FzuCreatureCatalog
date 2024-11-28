package com.catalog.controller.user;

import com.catalog.context.BaseContext;
import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Img;
import com.catalog.entity.Msg;
import com.catalog.mapper.FollowMapper;
import com.catalog.service.ImgService;
import com.catalog.service.UserService;
import com.catalog.dto.UserLoginDTO;
import com.catalog.entity.User;
import com.catalog.result.Result;
import com.catalog.utils.FileUtil;
import com.catalog.vo.UserLoginVO;
import com.catalog.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private FollowMapper followMapper;

    @PostMapping("/login")
    @ApiOperation("微信登入")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO)
    {
        log.info("WeChat User Login: code:{}", userLoginDTO.getCode());
        User user = userService.wxLogin(userLoginDTO);
        String userToken = userService.generateToken(user);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpen_id())
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
    public Result<List<Card>> getFollowCard(@RequestParam int sort_rule)
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getFollowCard.", userId);
        List<Card> cards = userService.getFollowCardsById(userId, sort_rule);
        return Result.success(cards);
    }
    @GetMapping("/info")
    @ApiOperation("获取用户个人信息")
    public Result<UserInfoVO> getUserOwnInfo()
    {
        int userId = BaseContext.getCurrentId();
        log.info("User:{} getUserInfo.", userId);
        User user = userService.getUserById(userId);
        Img userImage = imgService.getImageById(user.getImg_id());

        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setNickname(user.getNickname());
        userInfo.setImg_url(userImage.getUrl());
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
}
