package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.ArticleUpdateVo;
import com.noloafing.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult getHotArticleLIst();

    ResponseResult getArticleList(Integer pageNum,Integer pageSize,Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult saveArticle(Article article);

    ResponseResult getAllArticles(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult getArticleInfo(Long id);

    ResponseResult updateArticle(ArticleUpdateVo articleUpdateVo);

}
