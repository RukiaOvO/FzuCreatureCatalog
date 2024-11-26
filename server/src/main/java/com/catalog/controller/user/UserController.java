package com.catalog.controller.user;

import com.catalog.dto.GetOneCardDTO;
import com.catalog.dto.UserHomeCardDTO;
import com.catalog.entity.Card;
import com.catalog.entity.Msg;
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
        return Result.success();
    }
    @GetMapping("/notice")
    @ApiOperation("获取消息通知")
    public Result<List<Msg>> getMessage()
    {
        return Result.success();
    }
    @PutMapping("/read_msg")
    @ApiOperation("阅读单个消息")
    public Result<String> readOneMsg()
    {
        return Result.success();
    }
    @PutMapping("/read_all_msg")
    @ApiOperation("阅读所有消息")
    public Result<String> readAllMsg()
    {
        return Result.success();
    }
    @DeleteMapping("delete_msg")
    @ApiOperation("根据msg_id删除消息")
    public Result<String> deleteMsgById()
    {
        return Result.success();
    }
    @GetMapping("/home")
    @ApiOperation("首页获取卡片")
    public Result<List<Card>> getHomeCard(@RequestBody UserHomeCardDTO userHomeCardDTO)
    {
        return Result.success();
    }
    @GetMapping("/card")
    @ApiOperation("根据card_id获取卡片")
    public Result<Card> getOneCardById(@RequestBody GetOneCardDTO getOneCardDTO)
    {
        return Result.success();
    }
    @GetMapping("/my_card")
    @ApiOperation("获取用户自己的卡片")
    public Result<List<Card>> getUserOwnCard()
    {
        return Result.success();
    }
    @GetMapping("/info")
    @ApiOperation("获取用户个人信息")
    public Result<UserInfoVO> getUserOwnInfo()
    {
        return Result.success();
    }
    @PostMapping("/info")
    @ApiOperation("上传用户个人信息")
    public Result<String> uploadUserOwnInfo()
    {
        return Result.success();
    }

}
