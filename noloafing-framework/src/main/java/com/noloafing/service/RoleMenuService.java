package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.dto.RoleDto;
import com.noloafing.domain.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2022-10-13 17:01:18
 */

public interface RoleMenuService extends IService<RoleMenu> {

    ResponseResult updateRoleMenu(RoleDto roleDto);
}
