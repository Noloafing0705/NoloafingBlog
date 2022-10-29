package com.noloafing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.entity.UserRole;
import com.noloafing.mapper.UserRoleMapper;
import com.noloafing.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-10-21 15:48:30
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}

