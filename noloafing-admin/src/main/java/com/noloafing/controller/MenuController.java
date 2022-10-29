package com.noloafing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.MenuInfoVo;
import com.noloafing.domain.beanVO.MenuListVo;
import com.noloafing.domain.entity.Menu;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.service.MenuService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @PreAuthorize("@perm.hasPermission('system:menu:list')")
    @GetMapping("/list")
    public ResponseResult list(String status,String menuName){
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Menu::getStatus, status)
                    .like(StringUtils.hasText(menuName), Menu::getMenuName,menuName);
        List<Menu> menus = menuService.list(queryWrapper);
        List<MenuListVo> vos = BeanCopyUtils.copyBeanList(menus, MenuListVo.class);
        return ResponseResult.okResult(vos);
    }

    @PreAuthorize("@perm.hasPermission('system:menu:add')")
    @PostMapping()
    public ResponseResult add(@RequestBody Menu menu){
        boolean save = menuService.save(menu);
        return save == true ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    @PreAuthorize("@perm.hasPermission('system:menu:query')")
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id")Long id){
        Menu menu = menuService.getById(id);
        MenuInfoVo infoVo = BeanCopyUtils.copyBean(menu, MenuInfoVo.class);
        return ResponseResult.okResult(infoVo);
    }

    @PreAuthorize("@perm.hasPermission('system:menu:edit')")
    @PutMapping("")
    public ResponseResult edit(@RequestBody Menu menu){
        if (Objects.isNull(menu)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        //如果上层菜单与自身冲突
        if (menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_CONFLICT);
        }
        boolean b = menuService.updateById(menu);
        return b == true?ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    @PreAuthorize("@perm.hasPermission('system:menu:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id")Long id){
        Menu menu = menuService.getById(id);
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menu.getId());
        List<Menu> children = menuService.list(queryWrapper);
        if (children.size() > 0 ){
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_INCLUDE_CHILD);
        }
        boolean b = menuService.removeById(id);
        return b == true ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    @PreAuthorize("@perm.hasPermission('system:menu:list')")
    @GetMapping("/treeselect")
    public ResponseResult getMenuTree(){
        return menuService.getMenuTree();
    }

    @PreAuthorize("@perm.hasPermission('system:menu:list')")
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult getRoleMenuTreeselect(@PathVariable("id")Long id){
        return menuService.getRoleMenuTree(id);
    }


}
