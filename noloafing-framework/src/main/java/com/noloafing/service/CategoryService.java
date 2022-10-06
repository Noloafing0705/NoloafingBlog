package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.dto.CategoryDto;
import com.noloafing.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-08-09 20:54:45
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    ResponseResult listCategorys(Integer pageSize, Integer pageNum,String status,String name);

    ResponseResult saveCategory(CategoryDto categoryDto);

    ResponseResult updateCategory(CategoryDto categoryDto);

    ResponseResult updateStatus(CategoryDto categoryDto);
}
