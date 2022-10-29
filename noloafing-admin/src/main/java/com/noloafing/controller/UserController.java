package com.noloafing.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.ExportUser;
import com.noloafing.domain.dto.AddUserDto;
import com.noloafing.domain.dto.BatchIds;
import com.noloafing.domain.dto.UpdateUserDto;
import com.noloafing.domain.entity.User;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.service.UserService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.SecurityUtils;
import com.noloafing.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("system/user")
@PreAuthorize("@perm.hasPermission('system:user:list')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String userName,String phonenumber,String status){

        if (Objects.isNull(pageNum)||Objects.isNull(pageSize)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return userService.listUsers(pageNum,pageSize,userName,phonenumber,status);
    }

    @PreAuthorize("@perm.hasPermission('system:user:query')")
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id")Long id){
        if (Objects.isNull(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return userService.getUserDetailInfoById(id);
    }

    @PreAuthorize("@perm.hasPermission('system:user:add')")
    @PostMapping()
    public  ResponseResult add(@RequestBody AddUserDto addUserDto){
        if (Objects.isNull(addUserDto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return userService.addUser(addUserDto);
    }

    @PreAuthorize("@perm.hasPermission('system:user:edit')")
    @PutMapping()
    public ResponseResult update(@RequestBody UpdateUserDto updateUserDto){
        if (Objects.isNull(updateUserDto)||Objects.isNull(updateUserDto.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return userService.updateUser(updateUserDto);
    }

    @PreAuthorize("@perm.hasPermission('system:user:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id")Long id){
        if (Objects.isNull(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        if (SecurityUtils.getUserId().equals(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.CAN_NOT_OPERATE_CUR_USER);
        }
        return userService.deleteUserById(id);
    }

    @PreAuthorize("@perm.hasPermission('system:user:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            // 设置文件下载请求头
            WebUtils.setDownLoadHeader("用户信息.xlsx", response);
            // 获取导出的数据
            List<User> users = userService.list();
            // 转换到对应要导出的实体类
            List<ExportUser> userVoList = BeanCopyUtils.copyBeanList(users, ExportUser.class);
            for (ExportUser user: userVoList) {
                if (user.getAvatar() == null || ("").equals(user.getAvatar())){
                    continue;
                }
                user.setUrl(new URL(user.getAvatar()));
            }
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), ExportUser.class).autoCloseStream(Boolean.FALSE).sheet("模版")
                    .doWrite(userVoList);
        } catch (Exception e) {
            e.printStackTrace();
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }


    @PreAuthorize("@perm.hasPermission('system:user:remove')")
    @DeleteMapping("/batch")
    public ResponseResult deleteByIds(@RequestBody BatchIds batchIds){
        if (Objects.isNull(batchIds)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return ResponseResult.okResult();
    }
}
