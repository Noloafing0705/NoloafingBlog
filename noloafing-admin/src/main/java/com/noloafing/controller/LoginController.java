package com.noloafing.controller;


import com.noloafing.constant.MailConstants;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.AdminInfoVo;
import com.noloafing.domain.beanVO.MenuVo;
import com.noloafing.domain.beanVO.RoutersVo;
import com.noloafing.domain.beanVO.UserInfoVo;
import com.noloafing.domain.dto.AdminDto;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.LoginService;
import com.noloafing.service.MailService;
import com.noloafing.service.MenuService;
import com.noloafing.service.RoleService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.RedisCache;
import com.noloafing.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisCache redisCache;



    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody AdminDto user){
        if (Objects.isNull(user)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        if (!StringUtils.hasText(user.getUserName())){
           return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(user.getEmailCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_CHECK_CODE);
        }
        //从Redis中获取验证码
        String code = redisCache.getCacheObject(MailConstants.ADMIN_CODE + MailConstants.ADMIN_EMAIL);
        //如果验证码已经过期
        if (code == null){
            return  ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_CODE_EXPIRE);
        }
        //校验失败
        if (!code.equals(user.getEmailCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.CHECK_CODE_FAILED);
        }
        //如果正确,从redis中移除验证码
        redisCache.deleteObject(MailConstants.ADMIN_CODE + MailConstants.ADMIN_EMAIL);
        User longinUser = BeanCopyUtils.copyBean(user, User.class);
        return loginService.login(longinUser);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    @PostMapping("/user/verify")
    public ResponseResult verify(){
        return  loginService.sendAdminEmailCode();
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //如果不为空
        if (Objects.nonNull(loginUser)){
            Long userId = loginUser.getUser().getId();
            //获取menu信息
            List<String> permissions = menuService.getMenusById(userId);
            //获取role信息
            List<String> roles = roleService.getRolesById(userId);
            //获取user信息
            UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
            //vo封装
            AdminInfoVo adminInfoVo = new AdminInfoVo(permissions, roles, userInfoVo);
            return ResponseResult.okResult(adminInfoVo);
        }
        throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
    }

    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<MenuVo> menuVos = menuService.getRouters(userId);
        RoutersVo routersVo = new RoutersVo(menuVos);
        return ResponseResult.okResult(routersVo);
    }
}
