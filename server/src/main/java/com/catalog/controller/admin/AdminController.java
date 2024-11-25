package com.catalog.controller.admin;

import com.catalog.constant.MessageConstant;
import com.catalog.dto.AdminLoginDTO;
import com.catalog.result.Result;
import com.catalog.service.AdminService;
import com.catalog.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
