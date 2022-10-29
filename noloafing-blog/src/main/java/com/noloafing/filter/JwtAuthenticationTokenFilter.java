package com.noloafing.filter;

import com.alibaba.fastjson.JSON;
import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.LoginUser;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.utils.JwtUtil;
import com.noloafing.utils.RedisCache;
import com.noloafing.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(SystemConstant.BLOG_TOKEN);
        String refreshToken = request.getHeader(SystemConstant.REFRESH_TOKEN);

        //如果refreshToken不为空 说明需要刷新token
        if (StringUtils.hasText(refreshToken)){
            Claims cs = null;
            try {
                //如果refreshToken过期或不正确 解密失败
                cs = JwtUtil.parseJWT(refreshToken);
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new SystemException(AppHttpCodeEnum.REFRESH_TOKEN_EXPOSED);
            }
            String userId = cs.getSubject();
            String jwt = JwtUtil.createJWT(userId,null);
            Map<String, String> map = new HashMap<>();
            map.put(SystemConstant.BLOG_TOKEN, jwt);
            ResponseResult result =ResponseResult.okResult(map);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //如果没有token或"",说明接口不需要登录，放行,交由后续过滤器处理
        if(!StringUtils.hasText(token)){
           filterChain.doFilter(request, response);
           return;
        }
        //如果携带token有值
        Claims claims = null;
        try {
            //如果token过期或不正确 解密失败
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //需要重新登录
            e.printStackTrace();
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        LoginUser loginUser = redisCache.getCacheObject(SystemConstant.REDIS_PREFIX + userId);
        //如果缓存中的键值过期或者不存在，
        if (Objects.isNull(loginUser)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //又或者虽然token有效但是签发时间早于上一次登录时间说明该token过期 需要重新登录
        //获取该token的签发时间
        /*Date issuedAt = claims.getIssuedAt();
        if (issuedAt.getTime() < loginUser.getIssueAt().getTime()){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }*/

        //存入SecurityContextHolder
        //TODO 获取权限信息并存入
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
