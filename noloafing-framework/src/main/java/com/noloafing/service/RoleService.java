package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.dto.RoleDto;
import com.noloafing.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-08-20 16:51:15
 */
public interface RoleService extends IService<Role> {

    List<String> getRolesById(Long userId);

    ResponseResult getRoles(Integer pageNum, Integer pageSize, String status, String roleName);

    ResponseResult addRole(RoleDto roleDto);

    ResponseResult deleteRoleById(Long id);

    ResponseResult deleteRoleByIds(List<Long> ids);
}
