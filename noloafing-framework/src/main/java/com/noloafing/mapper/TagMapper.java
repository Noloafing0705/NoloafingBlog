package com.noloafing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noloafing.domain.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-17 15:33:17
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    List<String> getTagsByArticleId(@Param("id") Long id);
}

