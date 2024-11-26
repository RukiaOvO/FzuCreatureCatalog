package com.catalog.controller.user;

import com.catalog.constant.MessageConstant;
import com.catalog.context.BaseContext;
import com.catalog.entity.Msg;
import com.catalog.result.Result;
import com.catalog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "C端用户消息相关接口")
@Slf4j
public class MsgController
{
    @Autowired
    private UserService userService;

    @GetMapping("/notice")
    @ApiOperation("获取消息通知")
    public Result<List<Msg>> getMessage()
    {
        int userId = BaseContext.getCurrentId();
        List<Msg> msg = userService.getUserMsgById(userId);
        return Result.success(msg);
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
    public Result<String> deleteMsgById(@RequestParam int msgId)
    {
        Boolean hasMsg = false;
        int userId = BaseContext.getCurrentId();
        List<Msg> msgList = userService.getUserMsgById(userId);
        for(Msg msg : msgList)
        {
            if(msg.getId() == msgId)
            {
                hasMsg = true;
                break;
            }
        }
        if(hasMsg)
        {
            userService.deleteUserMsgByMsgId(msgId);
            return Result.success();
        }
        return Result.error(MessageConstant.MESSAGE_NOT_EXIST);
    }
}
