package com.noloafing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noloafing.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-20 16:50:09
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> getPartMenus(@Param("userId") Long userId);

    List<Menu> getAllMenus(Long userId);

    List<Menu> getMenusByUserId(Long userId);
}

