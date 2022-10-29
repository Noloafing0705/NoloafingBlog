package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.MenuVo;
import com.noloafing.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-08-20 16:50:10
 */
public interface MenuService extends IService<Menu> {

    List<String> getMenusById(Long userId);

    List<MenuVo> getRouters(Long userId);

    ResponseResult getMenuTree();

    ResponseResult getRoleMenuTree(Long id);
}
