package com.noloafing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.entity.Role;
import com.noloafing.mapper.RoleMapper;
import com.noloafing.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-08-20 16:51:15
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<String> getRolesById(Long userId) {
        return roleMapper.getPartRoles(userId);
    }
}

