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
import com.catalog.vo.UserLoginVO;
import com.catalog.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return Result.success();
    }

    @GetMapping("/follow")
    @ApiOperation("获取关注列表")
    public Result<List<Card>> getFollowCard()
    {
        int userId = BaseContext.getCurrentId();
        List<Card> cards = userService.getFollowCardsById(userId);
        return Result.success(cards);
    }
    @GetMapping("/info")
    @ApiOperation("获取用户个人信息")
    public Result<UserInfoVO> getUserOwnInfo()
    {
        int userId = BaseContext.getCurrentId();
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
    @PostMapping("/info")
    @ApiOperation("上传用户个人信息")
    public Result<String> uploadUserOwnInfo()
    {
        return Result.success();
    }

}
