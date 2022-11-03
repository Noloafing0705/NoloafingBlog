package com.noloafing.controller;

import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.Article;
import com.noloafing.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Api(value = "文章",tags = {"文章相关接口"})
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 获取置顶和viewCount最高的文章列表
     */
    @GetMapping("/hotArticleList")
    @ApiOperation(value = "热门文章列表",notes = "获取置顶或按浏览量排序的前十篇文章")
    public ResponseResult hotArticleList(){
        return articleService.getHotArticleLIst();
    }

    /**
     * 分页获取所有文章列表
     */
    @GetMapping("/articleList")
    @ApiOperation(value = "所有文章列表",notes = "分页获取所有文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小"),
            @ApiImplicitParam(name = "categoryId",value = "分类ID")
    })
    public ResponseResult getArticleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.getArticleList(pageNum,pageSize,categoryId);
    }

    /**
     * 查看文章详情
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "文章详情",notes = "获取全文以及相关信息")
    @ApiImplicitParam(name = "id",value = "文章id")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return  articleService.getArticleDetail(id);
    }

    /**
     * 更新文章浏览量
     */
    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "文章浏览量",notes = "动态更新文章浏览量")
    @ApiImplicitParam(name = "id",value = "文章id")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }


}
