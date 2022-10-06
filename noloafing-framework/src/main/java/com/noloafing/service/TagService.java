package com.noloafing.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.PageVo;
import com.noloafing.domain.beanVO.TagVo;
import com.noloafing.domain.dto.TagListDto;
import com.noloafing.domain.entity.Tag;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-08-17 15:33:17
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> listPageTags(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Integer id);

    ResponseResult updateTag(Tag tag);

    ResponseResult getTagById(Long id);


}
