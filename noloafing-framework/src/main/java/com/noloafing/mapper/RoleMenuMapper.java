package com.noloafing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noloafing.domain.entity.RoleMenu;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-13 17:01:16
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    List<Long> getMenuIdsByRoleId(Long id);
}

