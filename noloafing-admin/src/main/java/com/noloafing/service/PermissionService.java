package com.noloafing.service;

import com.noloafing.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("perm")
public class PermissionService {
    /**
     * 判断当前用户是否拥有权限
     */
    public boolean hasPermission(String perm){
        if (SecurityUtils.isAdmin()){
            return true;
        }
        List<String> perms = SecurityUtils.getLoginUser().getPerms();
        return perms.contains(perm);
    }
}
