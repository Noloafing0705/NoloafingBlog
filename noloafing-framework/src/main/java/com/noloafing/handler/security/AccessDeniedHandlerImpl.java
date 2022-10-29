package com.noloafing.handler.security;

import com.alibaba.fastjson.JSON;
import com.noloafing.domain.ResponseResult;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //输出异常信息
        accessDeniedException.printStackTrace();
        //处理授权异常
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
        return;
    }
}
