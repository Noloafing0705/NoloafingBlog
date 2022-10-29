package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.PageVo;
import com.noloafing.domain.beanVO.RoleVo;
import com.noloafing.domain.dto.RoleDto;
import com.noloafing.domain.entity.Role;
import com.noloafing.domain.entity.RoleMenu;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.mapper.MenuMapper;
import com.noloafing.mapper.RoleMapper;
import com.noloafing.mapper.RoleMenuMapper;
import com.noloafing.service.RoleService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> getRolesById(Long userId) {
        return roleMapper.getPartRoles(userId);
    }

    @Override
    public ResponseResult getRoles(Integer pageNum, Integer pageSize, String status, String roleName) {
        if (Objects.isNull(pageNum)||Objects.isNull(pageSize)||pageNum<=0||pageSize<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Role::getStatus, status)
                .like(StringUtils.hasText(roleName), Role::getRoleName,roleName);
        Page<Role> page = new Page<>(pageNum, pageSize);
        List<Role> records = page(page, queryWrapper).getRecords();
        List<RoleVo> list = BeanCopyUtils.copyBeanList(records, RoleVo.class);
        PageVo pageVo = new PageVo(list, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Transactional
    @Override
    public ResponseResult addRole(RoleDto roleDto) {
        List<String> menuIds = roleDto.getMenuIds();
        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        roleMapper.insert(role);
        menuIds.forEach(s -> roleMenuMapper.insert(new RoleMenu(role.getId(), Long.parseLong(s))));
        return ResponseResult.okResult();
    }

    @Transactional
    @Override
    public ResponseResult deleteRoleById(Long id) {
        roleMapper.deleteById(id);
        //role表是逻辑删除而menu_role关联表不是逻辑删除
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuMapper.delete(queryWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRoleByIds(@RequestBody List<Long> ids) {
        for (Long id :ids) {
            roleMapper.deleteById(id);
            //role表是逻辑删除而menu_role关联表不是逻辑删除
            LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(RoleMenu::getRoleId,id);
            roleMenuMapper.delete(queryWrapper);
        }
        return ResponseResult.okResult();
    }

}

