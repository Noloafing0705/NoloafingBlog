package com.noloafing.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.*;
import com.noloafing.domain.dto.AddUserDto;
import com.noloafing.domain.dto.UpdateUserDto;
import com.noloafing.domain.entity.Role;
import com.noloafing.domain.entity.User;
import com.noloafing.domain.entity.UserRole;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.mapper.RoleMapper;
import com.noloafing.mapper.UserMapper;
import com.noloafing.mapper.UserRoleMapper;
import com.noloafing.service.UserService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-08-11 10:13:10
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResponseResult getUserInfo(Long userId) {
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserInfoVo user) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId,user.getId())
                    .set(User::getAvatar,user.getAvatar())
                     .set(User::getNickName,user.getNickName())
                     .set(User::getSex,user.getSex());
        userMapper.update(new User(), updateWrapper);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult registerUser(User user) {
        //校验数据
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        //TODO 用户名、昵称合法性校验 邮箱、密码合法性校验
        if (!user.getPassword().equals(user.getRePassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_MAKE_SURE);
        }
        //检查用户名，昵称，邮箱是否已经存在
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //密码加密
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listUsers(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userName), User::getUserName,userName)
                    .eq(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber)
                    .eq(StringUtils.hasText(status),User::getStatus,status);
        Page<User> page = new Page<>(pageNum, pageSize);
        List<User> records = page(page, queryWrapper).getRecords();
        List<UserVo> list = BeanCopyUtils.copyBeanList(records, UserVo.class);
        PageVo pageVo = new PageVo(list, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Transactional
    @Override
    public ResponseResult addUser(AddUserDto addUserDto) {
        User user = BeanCopyUtils.copyBean(addUserDto, User.class);
        //校验数据
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(user.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_PHONE_NUMBER);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_EMAIL);
        }
        List<Long> roleIds = addUserDto.getRoleIds();
        if (roleIds == null || roleIds.size()==0){
            throw new SystemException(AppHttpCodeEnum.REQUIRED_ROLE);
        }
        //检查用户名，昵称，邮箱是否已经存在
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (phoneNumberExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        //密码加密
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //存入数据库
        save(user);
        roleIds.forEach(s -> userRoleMapper.insert(new UserRole(user.getId(), s)));
        return ResponseResult.okResult();
    }

    @Transactional
    @Override
    public ResponseResult getUserDetailInfoById(Long id) {
        User user = userMapper.selectById(id);
        UserDetailInfoVo userInfo = BeanCopyUtils.copyBean(user, UserDetailInfoVo.class);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserRole::getRoleId)
                    .eq(UserRole::getUserId,id);
        List<Long> roleIds = userRoleMapper.selectObjs(queryWrapper).stream().map(o -> (Long) o).collect(Collectors.toList());
        List<Role> roles = roleMapper.selectList(null);
        return ResponseResult.okResult(new UserUpdateInfoVo(roleIds, roles, userInfo));
    }

    @Transactional
    @Override
    public ResponseResult updateUser(UpdateUserDto updateUserDto) {
        List<Long> roleIds = updateUserDto.getRoleIds();
        User user = BeanCopyUtils.copyBean(updateUserDto, User.class);
        userMapper.updateById(user);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(queryWrapper);
        roleIds.forEach(roleId -> userRoleMapper.insert(new UserRole(user.getId(), roleId)));
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult deleteUserById(Long id) {
        deleteById(id);
        return ResponseResult.okResult();
    }

    public ResponseResult deleteUserByIds(List<Long> id) {
        return ResponseResult.okResult();
    }


    private void deleteById(Long id){
        userMapper.deleteById(id);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(queryWrapper);
    }

    private boolean userNameExist(String username){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        return count(queryWrapper)>0;
    }

    private boolean emailExist(String email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }
    private boolean nickNameExist(String nickname){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickname);
        return count(queryWrapper)>0;
    }

    private boolean phoneNumberExist(String phoneNumber){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phoneNumber);
        return count(queryWrapper)>0;
    }
}

