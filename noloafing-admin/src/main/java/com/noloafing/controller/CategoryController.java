package com.noloafing.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.noloafing.domain.ResponseResult;

import com.noloafing.domain.beanVO.DetailCategoryVO;
import com.noloafing.domain.dto.CategoryDto;
import com.noloafing.domain.entity.Category;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.CategoryService;

import com.noloafing.utils.BeanCopyUtils;
import com.noloafing.utils.WebUtils;
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

    @PreAuthorize("@perm.hasPermission('content:category:list')")
    @GetMapping("/list")
    public ResponseResult list(Integer pageSize,Integer pageNum,String status,String name){
        if (pageNum < 0 || pageSize <0){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return categoryService.listCategorys(pageSize,pageNum,status,name);
    }
    @PreAuthorize("@perm.hasPermission('content:category:list')")
    @GetMapping("/{id}")
    public ResponseResult getCategory(@PathVariable("id") Integer id){
        if (Objects.isNull(id) || id <= 0){
            throw  new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Category category = categoryService.getById(id);
        DetailCategoryVO categoryVO = BeanCopyUtils.copyBean(category, DetailCategoryVO.class);
        return ResponseResult.okResult(categoryVO);
    }
    @PreAuthorize("@perm.hasPermission('content:category:list')")
    @PutMapping()
    public ResponseResult updateCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.updateCategory(categoryDto);
    }
    @PreAuthorize("@perm.hasPermission('content:category:list')")
    @PutMapping("/status")
    public ResponseResult updateStatus(@RequestBody CategoryDto categoryDto){
        return categoryService.updateStatus(categoryDto);
    }
    @PreAuthorize("@perm.hasPermission('content:category:list')")
    @PostMapping()
    public ResponseResult addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.saveCategory(categoryDto);
    }
    @PreAuthorize("@perm.hasPermission('content:category:list')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id")String id){
        //??????????????????,???????????????rest?????????????????????
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
        // ???????????? ?????????????????????swagger ??????????????????????????????????????????????????????postman
        try {
            // ???????????????????????????
            WebUtils.setDownLoadHeader("??????.xlsx", response);
            // ?????????????????????
            List<Category> categoryList = categoryService.list();
            // ????????????????????????????????????
            List<DetailCategoryVO> voList = BeanCopyUtils.copyBeanList(categoryList, DetailCategoryVO.class);
            // ??????????????????????????????
            EasyExcel.write(response.getOutputStream(), DetailCategoryVO.class).autoCloseStream(Boolean.FALSE).sheet("??????")
                    .doWrite(voList);
        } catch (Exception e) {
            //??????????????????????????????json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

}
