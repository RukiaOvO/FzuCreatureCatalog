package com.catalog.controller.user;

import com.catalog.dto.UserLoginDTO;
import com.catalog.entity.User;
import com.catalog.result.Result;
import com.catalog.service.ImgService;
import com.catalog.service.UserService;
import com.catalog.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/img")
@Api(tags = "C端图片相关接口")
@Slf4j
public class ImgController
{
    @Autowired
    private ImgService imgService;

    @GetMapping("/test")
    @ApiOperation("test")
    public void test()
    {
        imgService.deleteImgInBed("ZGIRa1");
    }
}
