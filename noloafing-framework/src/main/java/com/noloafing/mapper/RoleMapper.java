package com.noloafing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noloafing.domain.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-20 16:51:15
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {
    List<String> getPartRoles(@Param("userId") Long userId);
}

