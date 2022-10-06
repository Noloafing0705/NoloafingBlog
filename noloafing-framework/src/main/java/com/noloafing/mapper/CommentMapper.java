package com.noloafing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.noloafing.domain.entity.Comment;
import org.springframework.stereotype.Repository;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-12 15:19:00
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}

