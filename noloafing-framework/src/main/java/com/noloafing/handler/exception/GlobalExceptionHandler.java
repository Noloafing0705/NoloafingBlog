package com.noloafing.handler.exception;


import com.noloafing.domain.ResponseResult;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //响应体控制层全局异常处理
@Slf4j //日志
public class GlobalExceptionHandler {
    //出现预期类的错误
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemException(SystemException e){
        log.error("出现预期内异常! {}",e);
        return ResponseResult.errorResult(e.getCode(),"WARNING! SYSTEM ERROR!");
    }

    // 由于全局异常会在自定义的AccessDeniedException异常处理类之前捕获该异常 所以返回信息不是我们自定义的
    // 这里需要手动捕获扔出去 让自定义的异常类生效
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        throw e;
    }

    //出现预期之外的错误
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        log.error("出现预期外异常! {}",e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"EXCEPTED ERROR!");
    }
}
