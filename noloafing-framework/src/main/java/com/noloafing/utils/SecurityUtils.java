package com.noloafing.utils;

import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    /**
     * 获取当前用户
     */
    public static LoginUser getLoginUser(){
        return (LoginUser) getAuthentication().getPrincipal();
    }
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 鉴别是否为管理员
     */
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(SystemConstant.SUPER_ADMIN_ID);
    }

    /**
     * 获取UserId
     */
    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }
}
