package com.noloafing.controller;

import com.noloafing.annotation.SystemLog;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.UserInfoVo;
import com.noloafing.domain.dto.RegistUserDto;
import com.noloafing.domain.entity.User;
import com.noloafing.service.UserService;
import com.noloafing.utils.RedisCache;
import com.noloafing.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(value = "用户",tags = {"用户相关接口"})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询用户信息
     */
    @GetMapping("/userInfo")
    @SystemLog(BusinessName = "用户信息展示")
    @ApiOperation(value = "用户信息展示",notes = "展示用户的基本信息")
    public ResponseResult getUserInfo(){
        Long userId = SecurityUtils.getUserId();
        return userService.getUserInfo(userId);
    }

    /**
     * 用户信息修改
     */
    @SystemLog(BusinessName = "更新个人中心用户信息")
    @PutMapping("/userInfo")
    @ApiOperation(value = "更改用户信息",notes = "更改用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserInfoVo user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @SystemLog(BusinessName = "用户注册")
    @ApiOperation(value = "用户注册",notes = "用户注册")
    public ResponseResult register(@RequestBody RegistUserDto userDto){
        return userService.registerUser(userDto);
    }

    @PostMapping("/verify")
    @SystemLog(BusinessName = "邮箱验证码")
    @ApiOperation(value = "邮箱验证",notes = "发送邮箱验证码")
    public ResponseResult getEmailCode(@RequestBody RegistUserDto userDto){
        return userService.sendEmailCode(userDto);
    }
}
