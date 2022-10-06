package com.noloafing.job;

import com.noloafing.constant.ArticleConstant;
import com.noloafing.domain.entity.Article;
import com.noloafing.service.ArticleService;
import com.noloafing.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 执行关于浏览量的相关定时任务类
 */
@Component
public class ViewCountJob {


    @Autowired
    RedisCache redisCache;

    @Autowired
    private ArticleService articleService;


    //从每小时0分开始每10min执行一次：从redis读取浏览量更新到数据库
    @Scheduled(cron = "0 0/10 * * * ?")
    public void updateViewCount(){
        Map<String, Integer> cacheMap = redisCache.getCacheMap(ArticleConstant.REDIS_VIEW_COUNT_KEY);
        List<Article> articles = cacheMap.entrySet()
                .stream()
                .map(new Function<Map.Entry<String, Integer>, Article>() {
                    @Override
                    public Article apply(Map.Entry<String, Integer> entry) {
                        return new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue());
                    }
                })
                .collect(Collectors.toList());
        articleService.updateBatchById(articles);
    }

}
