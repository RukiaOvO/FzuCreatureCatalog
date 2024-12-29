package com.catalog.controller.user;

import com.catalog.constant.MessageConstant;
import com.catalog.context.BaseContext;
import com.catalog.entity.Msg;
import com.catalog.mapper.MsgMapper;
import com.catalog.result.Result;
import com.catalog.service.MsgService;
import com.catalog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@Api(tags = "C端用户消息相关接口")
@Slf4j
public class MsgController
{
    @Autowired
    private UserService userService;
    @Autowired
    private MsgService msgService;

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
    public Result<String> readOneMsg(@RequestParam int msg_id)
    {
        int userId = BaseContext.getCurrentId();
        List<Integer> msgIds = userService.getMsgIdsById(userId);
        if(msgIds == null || msgIds.isEmpty() || !msgIds.contains(msg_id))
        {
            return Result.error(MessageConstant.MESSAGE_NOT_EXIST);
        }
        List<Integer> ids = new ArrayList<>();
        ids.add(msg_id);
        msgService.updateMsgByIds(ids);
        return Result.success(MessageConstant.READ_SUCCESS);
    }

    @PutMapping("/read_all_msg")
    @ApiOperation("阅读所有消息")
    public Result<String> readAllMsg()
    {
        int userId = BaseContext.getCurrentId();
        List<Integer> msgIds = userService.getMsgIdsById(userId);
        if(msgIds.isEmpty())
        {
            return Result.error(MessageConstant.MESSAGE_NOT_EXIST);
        }
        msgService.updateMsgByIds(msgIds);
        return Result.success();
    }

    @DeleteMapping("/delete_msg")
    @ApiOperation("根据msg_id删除消息")
    public Result<String> deleteMsgById(@RequestParam int msg_id)
    {
        int userId = BaseContext.getCurrentId();
        List<Integer> msgIds = userService.getMsgIdsById(userId);
        if(msgIds == null || msgIds.isEmpty() || !msgIds.contains(msg_id))
        {
            return Result.error(MessageConstant.MESSAGE_NOT_EXIST);
        }
        userService.deleteUserMsgByMsgId(msg_id);
        return Result.success();
    }
}
