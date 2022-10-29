package com.noloafing.domain.beanVO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentVo {
    //评论id
    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;
    //创建人ID
    private Long createBy;
    //评论创建时间
    private Date createTime;
    //用户名
    private String username;
    //评论回复的用户名
    private String toCommentUserName;
    //子评论
    private List<CommentVo> children;
    //用户头像
    private String avatar;
}
