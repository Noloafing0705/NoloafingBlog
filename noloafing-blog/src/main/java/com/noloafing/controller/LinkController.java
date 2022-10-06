package com.noloafing.controller;

import com.noloafing.domain.ResponseResult;
import com.noloafing.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(value = "友链",tags = {"友链相关接口"})
public class LinkController {

    @Autowired
    LinkService linkService;

    @GetMapping("/getAllLink")
    @ApiOperation(value = "友链展示",notes = "获取所有友链")
    public ResponseResult getLinks(){
        return linkService.getLinks();
    }
}
