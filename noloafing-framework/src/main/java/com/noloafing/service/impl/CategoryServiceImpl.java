package com.noloafing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noloafing.constant.ArticleConstant;
import com.noloafing.constant.CategoryConstant;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.CategoryVO;
import com.noloafing.domain.beanVO.DetailCategoryVO;
import com.noloafing.domain.beanVO.PageVo;
import com.noloafing.domain.dto.CategoryDto;
import com.noloafing.domain.entity.Article;
import com.noloafing.domain.entity.Category;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.mapper.ArticleMapper;
import com.noloafing.mapper.CategoryMapper;
import com.noloafing.service.CategoryService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-08-09 20:54:45
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询正常状态的文章
        queryWrapper.eq(Article::getStatus, ArticleConstant.STATUS_NORMAL);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        //获取分类列表ID的集合
        Set<Long> categoryIds = articles.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());
        //查询ID获得分类信息
        List<Category> categories = listByIds(categoryIds);
        //获取正常状态的分类
        categories = categories.stream().filter(category -> CategoryConstant.STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());
        //Vo封装
        List<CategoryVO> categoryVO = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);
        return ResponseResult.okResult(categoryVO);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,CategoryConstant.STATUS_NORMAL);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        List<CategoryVO> categoryVOList = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);
        return ResponseResult.okResult(categoryVOList);
    }

    @Override
    public ResponseResult listCategorys(Integer pageSize,Integer pageNum,String status,String name) {
        //分页查询所有的分类信息
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(status), Category::getStatus, status)
                    .like(StringUtils.hasText(name), Category::getName,name);
        Page<Category> page = new Page<>(pageNum, pageSize);
        List<Category> categories = page(page,queryWrapper).getRecords();
        List<DetailCategoryVO> categoryVOList = BeanCopyUtils.copyBeanList(categories, DetailCategoryVO.class);
        PageVo pageVo = new PageVo(categoryVOList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult saveCategory(CategoryDto categoryDto) {
        if (Objects.isNull(categoryDto)){
            throw  new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        if (!StringUtils.hasText(categoryDto.getName())){
            throw  new SystemException(AppHttpCodeEnum.REQUIRED_NAME);
        }
        if (!StringUtils.hasText(categoryDto.getStatus())){
            throw  new SystemException(AppHttpCodeEnum.REQUIRED_STATUS);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        int i = categoryMapper.insert(category);
        if (i <= 0){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return  ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateCategory(CategoryDto categoryDto) {
        if (Objects.isNull(categoryDto)){
            throw  new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        if (!StringUtils.hasText(categoryDto.getName())){
            throw  new SystemException(AppHttpCodeEnum.REQUIRED_NAME);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        int i = categoryMapper.updateById(category);
        if (i <= 0){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        return  ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateStatus(CategoryDto categoryDto) {
        if (Objects.isNull(categoryDto)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        if (!StringUtils.hasText(categoryDto.getStatus()) || categoryDto.getId() == null){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        categoryMapper.updateById(category);
        return ResponseResult.okResult();
    }
}

