package com.noloafing.controller;

import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.TagNameVo;
import com.noloafing.domain.dto.BatchIds;
import com.noloafing.domain.dto.TagDto;
import com.noloafing.domain.dto.TagListDto;
import com.noloafing.domain.entity.Tag;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.service.TagService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/content")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
         return tagService.listPageTags(pageNum,pageSize,tagListDto);
    }

    @PostMapping("/tag")
    public ResponseResult add(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("/tag/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id){
        return tagService.deleteTag(id);
    }

    @DeleteMapping("/tag")
    public ResponseResult deleteTagBatch(@RequestBody BatchIds batchIds){
        if (Objects.isNull(batchIds)||Objects.isNull(batchIds.getIds())||batchIds.getIds().size()==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        List<Long> ids = batchIds.getIds();
        return tagService.deleteTagByIds(ids);
    }

    @GetMapping("/tag/{id}")
    public ResponseResult getTagById(@PathVariable("id")Long id){
        return tagService.getTagById(id);
    }


    @PutMapping("/tag")
    public ResponseResult update(@RequestBody TagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        return tagService.updateTag(tag);
    }

    @GetMapping("/tag/listAllTag")
    public ResponseResult listAllCategory(){
        List<Tag> tags = tagService.list();
        List<TagNameVo> tagNameVos = BeanCopyUtils.copyBeanList(tags, TagNameVo.class);
        return ResponseResult.okResult(tagNameVos);
    }



}
