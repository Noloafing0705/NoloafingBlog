package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.dto.RoleDto;
import com.noloafing.domain.entity.Role;
import com.noloafing.domain.entity.RoleMenu;
import com.noloafing.mapper.RoleMapper;
import com.noloafing.mapper.RoleMenuMapper;
import com.noloafing.service.RoleMenuService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-10-13 17:01:18
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Transactional
    @Override
    public ResponseResult updateRoleMenu(RoleDto roleDto) {
        List<String> menuIds = roleDto.getMenuIds();
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        //修改角色
        roleMapper.updateById(role);
        //删除之前该角色对应的menu
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuMapper.delete(queryWrapper);
        //关联修改之后的menu
        menuIds.forEach(s -> roleMenuMapper.insert(new RoleMenu(role.getId(), Long.parseLong(s))));
        return ResponseResult.okResult();
    }
}

