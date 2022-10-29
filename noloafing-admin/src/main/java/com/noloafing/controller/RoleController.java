package com.noloafing.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.AddUserRoleVo;
import com.noloafing.domain.beanVO.RoleVo;
import com.noloafing.domain.dto.BatchStringIds;
import com.noloafing.domain.dto.ChangeStatusDto;
import com.noloafing.domain.dto.RoleDto;
import com.noloafing.domain.entity.Role;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.service.RoleMenuService;
import com.noloafing.service.RoleService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.WebUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("system/role")
@PreAuthorize("@perm.hasPermission('content:tag:index')")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private RoleMenuService roleMenuService;

    @PreAuthorize("@perm.hasPermission('system:role:list')")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String status,String roleName){
        return roleService.getRoles(pageNum,pageSize,status,roleName);
    }
    @PreAuthorize("@perm.hasPermission('system:role:edit')")
    @GetMapping("/listAllRole")
    public ResponseResult listRoleIds(){
        List<Role> roles = roleService.list();
        List<AddUserRoleVo> roleIdsVo = BeanCopyUtils.copyBeanList(roles, AddUserRoleVo.class);
        return ResponseResult.okResult(roleIdsVo);
    }
    @PreAuthorize("@perm.hasPermission('system:role:edit')")
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeDto){
        if (Objects.isNull(changeDto)||Objects.isNull(changeDto.getId())||Objects.isNull(changeDto.getStatus())){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        if ("1".equals(changeDto.getStatus()) || "0".equals(changeDto.getStatus())){
            Role role = BeanCopyUtils.copyBean(changeDto, Role.class);
            boolean update = roleService.updateById(role);
            return update == true ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    @PreAuthorize("@perm.hasPermission('system:role:add')")
    @PostMapping("")
    public ResponseResult add(@RequestBody RoleDto roleDto){
        if(Objects.isNull(roleDto)||Objects.isNull(roleDto.getMenuIds())||roleDto.getMenuIds().size()==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return roleService.addRole(roleDto);
    }

    @PreAuthorize("@perm.hasPermission('system:role:query')")
    @GetMapping("/{id}")
    public ResponseResult show(@PathVariable("id")Long id){
        if (Objects.isNull(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        Role role = roleService.getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @PreAuthorize("@perm.hasPermission('system:role:edit')")
    @PutMapping()
    public ResponseResult edit(@RequestBody RoleDto roleDto){
        if (Objects.isNull(roleDto) || Objects.isNull(roleDto.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return roleMenuService.updateRoleMenu(roleDto);
    }

    @PreAuthorize("@perm.hasPermission('system:role:remove')")
    @DeleteMapping()
    public ResponseResult deleteBatch(@RequestBody BatchStringIds batchIds){
        List<Long> ids = batchIds.getIds().stream().map(s -> Long.parseLong(s)).collect(Collectors.toList());
        return roleService.deleteRoleByIds(ids);
    }

    @PreAuthorize("@perm.hasPermission('system:role:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id")Long id){
        if (Objects.isNull(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        return roleService.deleteRoleById(id);
    }

    @PreAuthorize("@perm.hasPermission('system:role:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            // 设置文件下载请求头
            WebUtils.setDownLoadHeader("角色信息.xlsx", response);
            // 获取导出的数据
            List<Role> roles = roleService.list();
            // 转换到对应要导出的实体类
            List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(roles, RoleVo.class);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), RoleVo.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(roleVos);
        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

}

