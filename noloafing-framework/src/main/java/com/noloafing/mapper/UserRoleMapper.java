package com.noloafing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noloafing.domain.entity.UserRole;
import org.springframework.stereotype.Repository;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-21 15:48:29
 */

@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

