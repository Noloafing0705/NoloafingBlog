package com.noloafing.service.impl;

import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.LoginService;
import com.noloafing.utils.JwtUtil;
import com.noloafing.utils.RedisCache;
import com.noloafing.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public ResponseResult login(User user) {
        //获取用户名密码进行认证返回AuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //进行认证处理
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
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
        //用户信息存入Redis
        redisCache.setCacheObject(SystemConstant.REDIS_ADMIN_PREFIX +userId, loginUser);
        Map<String, String> map = new HashMap<>();
        map.put(SystemConstant.BLOG_TOKEN, jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        if (Objects.nonNull(userId)&&userId>0){
            redisCache.deleteObject(SystemConstant.REDIS_ADMIN_PREFIX+userId);
            return ResponseResult.okResult();
        }
        throw new SystemException(AppHttpCodeEnum.NEED_LOGIN);
    }
}
