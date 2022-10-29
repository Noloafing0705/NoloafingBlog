package com.noloafing.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.beanVO.LinkListVo;
import com.noloafing.domain.dto.BatchIds;
import com.noloafing.domain.dto.ChangeStatusDto;
import com.noloafing.domain.dto.LinkEditDto;
import com.noloafing.domain.dto.LinkShowDto;
import com.noloafing.domain.entity.Link;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.service.LinkService;
import com.noloafing.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @PreAuthorize("@perm.hasPermission('content:link:list')")
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String name,String status){
        return linkService.pageLink(pageNum,pageSize,name,status);
    }

    @PreAuthorize("@perm.hasPermission('content:link:query')")
    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id") Long id){
        if (Objects.isNull(id)||id <= 0){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getId, id);
        Link one = linkService.getOne(wrapper);
        LinkListVo vo = BeanCopyUtils.copyBean(one, LinkListVo.class);
        return ResponseResult.okResult(vo);
    }

    @PreAuthorize("@perm.hasPermission('content:link:add')")
    @PostMapping()
    public ResponseResult add(@RequestBody LinkShowDto linkShowDto){
        if (Objects.isNull(linkShowDto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        if (Objects.isNull(linkShowDto.getName())||Objects.isNull(linkShowDto.getLogo())||Objects.isNull(linkShowDto.getAddress())||Objects.isNull(linkShowDto.getStatus())||Objects.isNull(linkShowDto.getDescription())){
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        Link link = BeanCopyUtils.copyBean(linkShowDto, Link.class);
        boolean save = linkService.save(link);
        return save == true?ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }


    @PreAuthorize("@perm.hasPermission('content:link:edit')")
    @PutMapping()
    public ResponseResult edit(@RequestBody LinkEditDto linkEditDto){
        if (Objects.isNull(linkEditDto)||Objects.isNull(linkEditDto.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        if (Objects.isNull(linkEditDto.getName())||Objects.isNull(linkEditDto.getLogo())||Objects.isNull(linkEditDto.getAddress())||Objects.isNull(linkEditDto.getStatus())||Objects.isNull(linkEditDto.getDescription())){
            return ResponseResult.errorResult(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        Link link = BeanCopyUtils.copyBean(linkEditDto, Link.class);
        boolean updated = linkService.updateById(link);
        return updated == true?ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }
    @PreAuthorize("@perm.hasPermission('content:link:edit')")
    @PutMapping("/changeLinkStatus")
    public ResponseResult changeStatus(@RequestBody ChangeStatusDto changeStatus){
        if (Objects.isNull(changeStatus)||Objects.isNull(changeStatus.getId())|| "2".equals(changeStatus.getStatus())){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        Link link = BeanCopyUtils.copyBean(changeStatus, Link.class);
        boolean updated = linkService.updateById(link);
        return updated == true?ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    @PreAuthorize("@perm.hasPermission('content:link:remove')")
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id")Long id){
        if (Objects.isNull(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        boolean b = linkService.removeById(id);
        return b == true?ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }

    @PreAuthorize("@perm.hasPermission('content:link:remove')")
    @DeleteMapping()
    public ResponseResult batchDelete(@RequestBody BatchIds batchIds){
        if (Objects.isNull(batchIds)||batchIds.getIds().size() <= 1){
            return ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
        }
        List<Long> ids = batchIds.getIds();
        boolean b = linkService.removeBatchByIds(ids);
        return b == true?ResponseResult.okResult():ResponseResult.errorResult(AppHttpCodeEnum.OPERATE_FAILED);
    }
}
