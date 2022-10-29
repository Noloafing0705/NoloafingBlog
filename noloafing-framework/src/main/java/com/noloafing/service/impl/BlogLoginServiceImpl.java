package com.noloafing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.BlogUserLoginVo;
import com.noloafing.domain.beanVO.UserInfoVo;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.mapper.UserMapper;
import com.noloafing.service.BlogLoginService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.JwtUtil;
import com.noloafing.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl extends ServiceImpl<UserMapper, User> implements BlogLoginService {

    @Autowired
    private RedisCache redisCache;
    @Resource
    private AuthenticationManager authenticationManager;


    @Override
    public ResponseResult login(User user) {
        //获取用户名密码进行认证 返回AuthenticationToken对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //进行身份认证处理
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果为null,说明用户名或密码错误
        if (Objects.isNull(authenticate)) {
           throw new RuntimeException("用户名或密码错误");
        }
        //获取认证之后的LoginUser
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //系统当前时间 记作登录时间
        long nowMillis = System.currentTimeMillis();
        String jwt = JwtUtil.createJWT(userId,nowMillis);
        loginUser.setIssueAt(new Date(nowMillis));
        //refreshToken
        String refreshJwt = JwtUtil.createJWT(userId, JwtUtil.JWT_TTL_WEEK, null);
        //用户信息存入Redis
        redisCache.setCacheObject(SystemConstant.REDIS_PREFIX +userId, loginUser);
        //token 与 userInfo进行封装，返回前端
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt,refreshJwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }
}
