package com.noloafing.controller;

import com.noloafing.domain.ResponseResult;
import com.noloafing.domain.entity.Resourcewall;
import com.noloafing.service.ResourcewallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResourceController {

    @Autowired
    private ResourcewallService resourcewallService;

    @GetMapping("/resourceWall/list")
    public ResponseResult list(){
        List<Resourcewall> list = resourcewallService.list();
        return ResponseResult.okResult(list);
    }

}
