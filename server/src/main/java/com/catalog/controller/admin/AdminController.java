package com.catalog.controller.admin;

import com.catalog.constant.MessageConstant;
import com.catalog.dto.AdminLoginDTO;
import com.catalog.dto.RejectCardDTO;
import com.catalog.result.Result;
import com.catalog.service.AdminService;
import com.catalog.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
@Slf4j
public class AdminController
{
    @Autowired
    private AdminService adminService;

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
    public Result<String> showHomeCards()
    {
        adminService.showHomeCards();
        return Result.success();
    }

    @DeleteMapping("/reject_card")
    @ApiOperation("删除卡片")
    public Result<String> deleteRejectCard(@RequestBody RejectCardDTO rejectCardDTO)
    {
        adminService.deleteCard(rejectCardDTO);
        return Result.success();
    }
}
