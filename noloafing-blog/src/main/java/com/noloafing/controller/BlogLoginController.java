package com.noloafing.controller;

import com.google.code.kaptcha.Constants;
import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.dto.LoginUserDto;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.BlogLoginService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.RedisCache;
import com.noloafing.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Api(value = "用户登录",tags = {"用户登录接口"})
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @Autowired
    private RedisCache redisCache;


    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "用户登录")
    public ResponseResult login(HttpServletRequest request, @RequestBody LoginUserDto userDto){
        if (!StringUtils.hasText(userDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(userDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(userDto.getCheckCode())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_CHECK_CODE);
        }
        HttpSession session = request.getSession();
        String code =(String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!userDto.getCheckCode().equals(code)){
            throw new SystemException(AppHttpCodeEnum.CHECK_CODE_FAILED);
        }
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录",notes = "退出登录")
    public ResponseResult logout(){
        Long userId = SecurityUtils.getUserId();
        //从redis中移除键值对信息
        redisCache.deleteObject(SystemConstant.REDIS_PREFIX+userId);
        return ResponseResult.okResult();
    }
}
