package com.noloafing.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.constant.MailConstants;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.*;
import com.noloafing.domain.dto.AddUserDto;
import com.noloafing.domain.dto.RegistUserDto;
import com.noloafing.domain.dto.UpdateUserDto;
import com.noloafing.domain.entity.Role;
import com.noloafing.domain.entity.User;
import com.noloafing.domain.entity.UserRole;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.mapper.RoleMapper;
import com.noloafing.mapper.UserMapper;
import com.noloafing.mapper.UserRoleMapper;
import com.noloafing.service.MailService;
import com.noloafing.service.UserService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.DateUtils;
import com.noloafing.utils.MailCodeUtils;
import com.noloafing.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisCache redisCache;

    @Resource
    private TemplateEngine templateEngine;

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
    public ResponseResult registerUser(RegistUserDto  userDto) {
        if (Objects.isNull(userDto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        if (!StringUtils.hasText(userDto.getEmailCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_CHECK_CODE);
        }
        //校验数据
        if (!StringUtils.hasText(userDto.getUserName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(userDto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_PASSWORD);
        }
        if (!StringUtils.hasText(userDto.getNickName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_NICKNAME);
        }
        if (!StringUtils.hasText(userDto.getEmail())){
            return ResponseResult.errorResult(AppHttpCodeEnum.REQUIRE_EMAIL);
        }

        //确认密码是否成功
        if (!userDto.getPassword().equals(userDto.getRePassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_MAKE_SURE);
        }
        //确认邮箱是否符合
        if(!userDto.getEmail().matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}")){
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_FORMAT_FAILED);
        }
        //确认用户名是否符合 6-12位英文字母+数字+下划线
        if (!userDto.getUserName().matches("^[a-zA-Z0-9_]{6,15}$")){
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_FORMAT_FAILED);
        }
        //确认昵称是否符合 长度2-8 只能包含中英文和数字
        if (!userDto.getNickName().matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]{2,8}$")){
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_FORMAT_FAILED);
        }
        //确认密码是否符合 至少包含一位字母和特殊字符 长度9-16
        if (!userDto.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[^\\da-zA-Z\\s]).{9,16}$")){
            return ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_FORMAT_FAILED);
        }
        //检查用户名，昵称，邮箱是否已经存在
        if (userNameExist(userDto.getUserName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (nickNameExist(userDto.getNickName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if (emailExist(userDto.getEmail())){
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //获取redis中的验证码对比
        String code = redisCache.getCacheObject(userDto.getEmail() + MailConstants.EMAIL_CODE);
        if (code == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_CODE_EXPIRE);
        }
        if (!userDto.getEmailCode().equals(code)){
            return ResponseResult.errorResult(AppHttpCodeEnum.CHECK_CODE_FAILED);
        }
        //验证码通过删除该验证码
        redisCache.deleteObject(userDto.getEmail() + MailConstants.EMAIL_CODE);
        //密码加密
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        User user = BeanCopyUtils.copyBean(userDto, User.class);
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


    @Transactional
    @Override
    public ResponseResult deleteUserById(Long id) {
        deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult sendEmailCode(RegistUserDto userDto) {
        Boolean validUser = validRegisterUserInfo(userDto);
        if (validUser){
            //检查用户名，昵称，邮箱是否已经存在
            if (userNameExist(userDto.getUserName())){
                return ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_EXIST);
            }
            if (nickNameExist(userDto.getNickName())){
                return ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
            }
            if (emailExist(userDto.getEmail())){
                return ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_EXIST);
            }

            //获取要发送对象的邮箱
            String  toSend = userDto.getEmail();
            // 设置该邮箱申请发送的次数
            String userCountKey = toSend+ MailConstants.EMAIL_TIMES;
            Integer times = redisCache.getCacheObject(userCountKey);
            // 如果第一次请求验证码
            if(times == null){
                //设置次数为1
                redisCache.setCacheObject(userCountKey,1);
                redisCache.expire(userCountKey, 60 * 30);
            }else if(times <=4){
                //半小时内请求5次以内
                // 每一次请求次数会加一
                redisCache.setCacheObject(userCountKey,times+1);
            }else{ //time >= 5 半小时内连续请求五次 当天封禁
                Integer secondsOneDay = DateUtils.getRemainSecondsOneDay(new Date());
                redisCache.expire(userCountKey, secondsOneDay, TimeUnit.SECONDS);
                return ResponseResult.errorResult(AppHttpCodeEnum.ADMIN_CODE_REQUEST_MUCH);
            }
            // 1.生成校验码
            String code = MailCodeUtils.getCode();
            // 2.设置邮件html内容
            String message = "详情：您正在尝试使用该邮箱注册操作，若非是您本人的行为，请忽略!";
            Context context = new Context();
            context.setVariable("message", message);
            context.setVariable("code", code);
            String mail = templateEngine.process("mailtemplate.html", context);
            // 3.发送邮件
            mailService.sendHtmlMail(toSend, "邮箱验证码", mail);
            // 3.发送成功后 存到redis 以邮箱为key value为校验码 设置过期时间 以及次数
            // 键值对
            redisCache.setCacheObject(toSend+MailConstants.EMAIL_CODE, code);
            // 设置过期时间（2分钟内有效）
            redisCache.expire(toSend+MailConstants.EMAIL_CODE, 60 * 2);
            //返回信息
            return  ResponseResult.okResult();
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.REGISTER_INFO_ERROR);
    }

    @Transactional
    @Override
    public ResponseResult deleteUserByIds(List<Long> id) {
        id.forEach(once -> deleteById(once));
        return ResponseResult.okResult();
    }

    //校验注册用户的信息是否有效
    private Boolean validRegisterUserInfo(RegistUserDto userDto){
        if (Objects.isNull(userDto)){
            return false;
        }
        //校验数据
        if (!StringUtils.hasText(userDto.getUserName())){
            return false;
        }
        if (!StringUtils.hasText(userDto.getPassword())){
            return false;
        }
        if (!StringUtils.hasText(userDto.getNickName())){
            return false;
        }
        if (!StringUtils.hasText(userDto.getEmail())){
            return false;
        }

        //确认密码是否成功
        if (!userDto.getPassword().equals(userDto.getRePassword())){
            return false;
        }
        //确认邮箱是否符合
        if(!userDto.getEmail().matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}")){
            return false;
        }
        //确认用户名是否符合 6-12位英文字母+数字+下划线
        if (!userDto.getUserName().matches("^[a-zA-Z0-9_]{6,15}$")){
            return false;
        }
        //确认昵称是否符合 长度2-8 只能包含中英文和数字
        if (!userDto.getNickName().matches("^[\\u4e00-\\u9fa5a-zA-Z0-9]{2,8}$")){
            return false;
        }
        //确认密码是否符合 至少包含一位字母和特殊字符 长度9-16
        if (!userDto.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[^\\da-zA-Z\\s]).{9,16}$")){
            return false;
        }
        return true;
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

