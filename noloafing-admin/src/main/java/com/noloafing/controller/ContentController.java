package com.noloafing.controller;

import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.ArticleUpdateVo;
import com.noloafing.domain.dto.ArticleDto;
import com.noloafing.domain.dto.BatchIds;
import com.noloafing.domain.entity.Article;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.*;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
public class ContentController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private ArticleService articleService;

    @PreAuthorize("@perm.hasPermission('content:article:list')")
    @GetMapping("/content/category/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@perm.hasPermission('content:article:writer')")
    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile img){
        try {
            return uploadService.uploadImg(img);
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException("图片上传失败");
        }

    }

    @PreAuthorize("@perm.hasPermission('content:article:writer')")
    @PostMapping("/content/article")
    public ResponseResult writeArticle(@RequestBody ArticleDto articleDto){
        if (Objects.isNull(articleDto)|| !StringUtils.hasText(articleDto.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        if (!StringUtils.hasText(articleDto.getTitle())){
            throw new SystemException(AppHttpCodeEnum.REQUIRED_TITLE);
        }
        if (!StringUtils.hasText(articleDto.getSummary())){
            throw new SystemException(AppHttpCodeEnum.REQUIRED_SUMMARY);
        }
        if (Objects.isNull(articleDto.getTags())){
            throw new SystemException(AppHttpCodeEnum.REQUIRED_TAG);
        }
        if (Objects.isNull(articleDto.getCategoryId())){
            throw new SystemException(AppHttpCodeEnum.REQUIRED_CATEGORY);
        }
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        return articleService.saveArticle(article);
    }

    @PreAuthorize("@perm.hasPermission('content:article:list')")
    @GetMapping("/content/article/list")
    public ResponseResult listArticle(Integer pageNum,Integer pageSize,String title,String summary ){
        return  articleService.getAllArticles(pageNum,pageSize,title,summary);
    }

    @PreAuthorize("@perm.hasPermission('content:article:list')")
    @GetMapping("/content/article/{id}")
    public ResponseResult getArticleInfo(@PathVariable("id")Long id){
        return  articleService.getArticleInfo(id);
    }

    @PreAuthorize("@perm.hasPermission('content:article:list')")
    @PutMapping("/content/article")
    public ResponseResult update(@RequestBody ArticleUpdateVo articleUpdateVo){
        return articleService.updateArticle(articleUpdateVo);
    }

    @PreAuthorize("@perm.hasPermission('content:article:list')")
    @DeleteMapping("/content/article/{id}")
    public ResponseResult deleteArticle(@PathVariable("id")Long id){
        if (Objects.isNull(id)||id<=0){
           return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        boolean b = articleService.removeById(id);
        return b == true ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    @PreAuthorize("@perm.hasPermission('content:article:list')")
    @DeleteMapping("/content/article/")
    public ResponseResult deleteArticle(@RequestBody BatchIds batchIds){
        if (Objects.isNull(batchIds) || batchIds.getIds().size()<=0){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        List<Long> ids = batchIds.getIds();
        boolean b = articleService.removeBatchByIds(ids);
        return b == true ? ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

}
