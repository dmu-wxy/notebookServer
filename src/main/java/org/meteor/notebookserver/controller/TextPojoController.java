package org.meteor.notebookserver.controller;


import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.meteor.notebookserver.pojo.TextPojoImp;
import org.meteor.notebookserver.pojo.User;
import org.meteor.notebookserver.service.TextPojoImpService;
import org.meteor.notebookserver.util.JwtConUtil;
import org.meteor.notebookserver.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/text")
public class TextPojoController {

    @Autowired
    private TextPojoImpService textPojoImpService;
    Logger logger = LoggerFactory.getLogger(TextPojoController.class);

    @PostMapping("/upload")
    public RespBean updateTextPojo(@RequestBody List<TextPojoImp> textPojoImps,@RequestHeader String token){
        User user = JwtConUtil.geTokenInfo(token);
        if (user == null)
            return RespBean.AUTH_ERROR("您还没有登录");
        return textPojoImpService.updateTextPojo(textPojoImps,user.getId());
    }
    @GetMapping("/getAll")
    public RespBean getAll(@RequestHeader String token){
        User user = JwtConUtil.geTokenInfo(token);
        if (user == null)
            return RespBean.AUTH_ERROR("您还没有登录");
        logger.info(String.valueOf(textPojoImpService.getAll(user.getId()).getData().size()));
        return RespBean.ok("success",textPojoImpService.getAll(user.getId()));
    }
    @PostMapping("/remove")
    public RespBean remove(Long id,@RequestHeader String token){
        User user = JwtConUtil.geTokenInfo(token);
        if (user==null){
            return RespBean.AUTH_ERROR("您还没有登录");
        }
        return RespBean.ok("success",textPojoImpService.remove(id,user.getId()));

    }

}
