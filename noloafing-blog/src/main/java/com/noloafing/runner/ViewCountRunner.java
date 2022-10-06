package com.noloafing.runner;

import com.noloafing.constant.ArticleConstant;
import com.noloafing.domain.entity.Article;
import com.noloafing.service.ArticleService;
import com.noloafing.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 在项目初始化完成后执行（项目启动时执行）,读取ViewCount存入Redis中
 *
 */
@Component
public class ViewCountRunner implements CommandLineRunner {


    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    //以id:viewCount形式存入redis
    @Override
    public void run(String... args) throws Exception {
        List<Article> articles = articleService.list();
        Map<String, Integer> viewCountMap = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap(ArticleConstant.REDIS_VIEW_COUNT_KEY, viewCountMap);
    }
}
