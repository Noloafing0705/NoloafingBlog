package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.constant.SystemConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.MenuTreeVo;
import com.noloafing.domain.beanVO.MenuVo;
import com.noloafing.domain.beanVO.MenusCheckedKeys;
import com.noloafing.domain.entity.Menu;
import com.noloafing.domain.entity.RoleMenu;
import com.noloafing.mapper.MenuMapper;
import com.noloafing.mapper.RoleMenuMapper;
import com.noloafing.service.MenuService;
import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-08-20 16:50:10
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<String> getMenusById(Long userId) {
        //如果是超级管理员，返回所有的menu
        List<String> menus = null;
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(Menu::getPerms)
                    .in(Menu::getMenuType,SystemConstant.MENU,SystemConstant.BUTTON)
                    .eq(Menu::getStatus,SystemConstant.NORMAL_STATUS);
            return menuMapper.selectObjs(queryWrapper).stream().map(o -> (String) o).collect(Collectors.toList());
        }
        //不是超级管理员
        return menuMapper.getPartMenus(userId);
    }

    @Override
    public List<MenuVo> getRouters(Long userId) {
        //如果是管理员
        List<Menu> menus = null;
        if (SecurityUtils.isAdmin()){
            //查询所有正常的的菜单
            menus = menuMapper.getAllMenus(userId);
        }else{
            //查询当前用户对应的菜单
            menus = menuMapper.getMenusByUserId(userId);
        }
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        //构建tree 父子层级菜单
        List<MenuVo> router = buildTreeMenuRouter(menuVos,0L);
        return router;
    }

    @Override
    public ResponseResult getMenuTree() {
        return ResponseResult.okResult(getMenuTreeSelect());
    }

    /**
     * 根据角色id返回角色关联的菜单树以及关联的菜单id
     */
    @Transactional
    @Override
    public ResponseResult getRoleMenuTree(Long id) {
        List<MenuTreeVo> menus = getMenuTreeSelect();
        List<Long> checkedKeys = roleMenuMapper.getMenuIdsByRoleId(id);
        return ResponseResult.okResult(new MenusCheckedKeys(menus, checkedKeys));
    }

    //获取菜单树
    private List<MenuTreeVo> getMenuTreeSelect(){
        List<Menu> menus = menuMapper.selectList(null);
        List<MenuTreeVo> treeVos = menus.stream().map(menu -> new MenuTreeVo(new ArrayList<>(), menu.getId(), menu.getMenuName(), menu.getParentId())).collect(Collectors.toList());
        List<MenuTreeVo> menuTree = buildTreeMenuTree(treeVos, 0L);
        return  menuTree;
    }

    /**
     * 根据parentId找到第一层级菜单列表，对其中每一个菜单，
     * 都有获取其子菜单列表的方法，然后设置子菜单列表到该菜单children属性中
     */
    private List<MenuVo> buildTreeMenuRouter(List<MenuVo> menus,Long parentId) {
        return menus.stream()
                .filter(menu -> parentId.equals(menu.getParentId()))
                //获取该菜单下的子菜单，并设置到children属性中
                .map(menu -> menu.setChildren(getChildrenMenus(menu,menus)))
                .collect(Collectors.toList());
    }

    private List<MenuTreeVo> buildTreeMenuTree(List<MenuTreeVo> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> parentId.equals(menu.getParentId()))
                //获取该菜单下的子菜单，并设置到children属性中
                .map(menu -> menu.setChildren(getChildrenMenusTree(menu,menus)))
                .collect(Collectors.toList());
    }

    /**
     * 在menus中获取menu的子菜单
     */
    private List<MenuVo> getChildrenMenus(MenuVo menu, List<MenuVo> menus) {
        return menus.stream()
                .filter(m ->m.getParentId().equals(menu.getId()))
                //考虑到三级甚至多级菜单的情况下，
                // 需要继续找到子菜单的子菜单...并设置children属性,
                // 于是这里继续遍历每个菜单，寻找其子菜单并添加，调用自身的找子菜单方法，实现递归
                .map(m -> m.setChildren(getChildrenMenus(m, menus)))
                .collect(Collectors.toList());
    }

    private List<MenuTreeVo> getChildrenMenusTree(MenuTreeVo menu, List<MenuTreeVo> menus) {
        return menus.stream()
                .filter(m ->m.getParentId().equals(menu.getId()))
                //考虑到三级甚至多级菜单的情况下，
                // 需要继续找到子菜单的子菜单...并设置children属性,
                // 于是这里继续遍历每个菜单，寻找其子菜单并添加，调用自身的找子菜单方法，实现递归
                .map(m -> m.setChildren(getChildrenMenusTree(m, menus)))
                .collect(Collectors.toList());
    }

}

