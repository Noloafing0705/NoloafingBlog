package com.noloafing.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.noloafing.domain.ResponseResult;

import com.noloafing.domain.beanVO.CategoryVO;
import com.noloafing.domain.beanVO.DetailCategoryVO;
import com.noloafing.domain.dto.CategoryDto;
import com.noloafing.domain.entity.Category;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.CategoryService;

import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.WebUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseResult list(@Param("pageSize") Integer pageSize,@Param("pageNum")Integer pageNum,@Param("status")String status,@Param("name")String name){
        if (pageNum < 0 || pageSize <0){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return categoryService.listCategorys(pageSize,pageNum,status,name);
    }

    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable("id") Integer id){
        if (Objects.isNull(id) || id <= 0){
            throw  new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Category category = categoryService.getById(id);
        DetailCategoryVO categoryVO = BeanCopyUtils.copyBean(category, DetailCategoryVO.class);
        return ResponseResult.okResult(categoryVO);
    }

    @PutMapping()
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }

    @PutMapping("/status")
    public ResponseResult updateStatus(@RequestBody CategoryDto categoryDto){
        return categoryService.updateStatus(categoryDto);
    }

    @PostMapping()
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.saveCategory(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id")String id){
        //TODO 暂时先以切分数组的delete请求方式批量删除 后续优化前端 使用PUT单独完成批量删除
        String[] arr = id.split(",");
        List<Long> ids = new ArrayList<>();
        for (String str:arr) {
            ids.add(Long.parseLong(str));
        }
        boolean b = categoryService.removeBatchByIds(ids);
        if (!b){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return ResponseResult.okResult();
    }

    @PreAuthorize("@perm.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            // 设置文件下载请求头
            WebUtils.setDownLoadHeader("分类.xlsx", response);
            // 获取导出的数据
            List<Category> categoryList = categoryService.list();
            // 转换到对应要导出的实体类
            List<DetailCategoryVO> voList = BeanCopyUtils.copyBeanList(categoryList, DetailCategoryVO.class);
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), DetailCategoryVO.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(voList);
        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

}
