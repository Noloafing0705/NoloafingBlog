package com.noloafing.controller;

import com.noloafing.domain.ResponseResult;
import com.noloafing.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(value = "文章分类",tags = {"文章分类接口"})
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @ApiOperation(value = "文章分类",notes = "获取文章的分类")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
