package com.noloafing.service.impl;

import com.noloafing.constant.MailConstants;
import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.domain.entity.User;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.LoginService;
import com.noloafing.service.MailService;
import com.noloafing.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private TemplateEngine templateEngine;

    @Autowired
    private MailService mailService;


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

    /**
     * 给管理员发送验证码
     * @return
     */
    @Override
    public ResponseResult sendAdminEmailCode() {
        String adminUser = MailConstants.ADMIN_CODE_COUNT + MailConstants.ADMIN_EMAIL;
        //获取请求验证码次数
        Integer count = redisCache.getCacheObject(adminUser);
        //如果为空：首次请求验证码
        if (count == null){
            //设置次数为1
            redisCache.setCacheObject(adminUser, 1);
        }else if (count <= 4){
            //次数为1-4之间 次数+1
            redisCache.setCacheObject(adminUser, count+1);
        }else{
            //次数 >=5
            //获取当天剩下的时间
            Integer seconds = DateUtils.getRemainSecondsOneDay(new Date());
            redisCache.expire(adminUser, seconds, TimeUnit.SECONDS);
            return ResponseResult.errorResult(AppHttpCodeEnum.ADMIN_CODE_REQUEST_MUCH);
        }
        // 1.生成校验码
        String code = MailCodeUtils.getCode();
        // 2.设置邮件html内容
        String message = "详情：您正在尝试以管理员身份登录后台管理系统，若非是您本人的行为，请立即处理!";
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("code", code);
        String mail = templateEngine.process("mailtemplate.html", context);
        // 3.发送邮件
        mailService.sendHtmlMail(MailConstants.ADMIN_EMAIL, "邮箱验证码", mail);
        // 3.发送成功后 存到redis 以邮箱为key value为校验码 设置过期时间 以及次数
        // 键值对
        redisCache.setCacheObject(MailConstants.ADMIN_CODE+MailConstants.ADMIN_EMAIL, code);
        // 设置过期时间（2分钟内有效）
        redisCache.expire(MailConstants.ADMIN_CODE+MailConstants.ADMIN_EMAIL, 60 * 2);
        //返回信息
        return  ResponseResult.okResult();
    }
}
