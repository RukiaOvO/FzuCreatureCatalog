package com.catalog.controller.user;

import com.catalog.entity.Msg;
import com.catalog.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Api(tags = "C端用户消息相关接口")
@Slf4j
public class MsgController
{
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
}
