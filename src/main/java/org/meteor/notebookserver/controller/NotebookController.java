package org.meteor.notebookserver.controller;

import io.jsonwebtoken.Claims;
import org.meteor.notebookserver.entity.Notebook;
import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.meteor.notebookserver.service.NotebookService;
import org.meteor.notebookserver.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notebook")
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    Logger logger = LoggerFactory.getLogger(NotebookController.class);

    @PostMapping("/upload")
    public RespBean updateNotebooks(@RequestBody List<Notebook> notebooks, @RequestHeader String token){
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        logger.info(notebooks.toString());
        return notebookService.updateNotebook(notebooks,userInfo.getId());
    }

    @GetMapping("/download")
    public RespBean downloadNotebooks(@RequestHeader String token){
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        return RespBean.ok("同步成功",notebookService.downloadNotebook(userInfo.getId()));
    }

    @GetMapping("/test")
    public String test(String name){
        System.out.println(name);
        return name + "!!!";
    }
}
