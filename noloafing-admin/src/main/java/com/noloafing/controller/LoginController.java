package com.noloafing.controller;


import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.AdminInfoVo;
import com.noloafing.domain.beanVO.MenuVo;
import com.noloafing.domain.beanVO.RoutersVo;
import com.noloafing.domain.beanVO.UserInfoVo;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.LoginService;
import com.noloafing.service.MenuService;
import com.noloafing.service.RoleService;
import com.noloafing.utils.BeanCopyUtils;
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


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        return loginService.login(user);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
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
