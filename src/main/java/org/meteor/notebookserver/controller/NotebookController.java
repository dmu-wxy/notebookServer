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
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @PostMapping("/uploadFile")
    public RespBean updateNotebookFiles(MultipartFile notebook,@RequestHeader String token) throws IOException {
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        File path = ResourceUtils.getFile("classpath:");
        File uploadDir = new File(path.getAbsolutePath(),"static/file");

        synchronized (NotebookController.class){
            String fileName = notebook.getOriginalFilename();
            // data\\user\\0\\org.meteor.notebook\\files".length()
            fileName = fileName.substring(38);
            String filePath = uploadDir.getAbsolutePath() + "/" + userInfo.getId();
            // 保存文件
            File saveFile = new File(filePath + "/" + fileName);
            logger.info(saveFile.getPath());
            if(!saveFile.exists()){
                saveFile.mkdirs();
            }
            notebook.transferTo(saveFile);
        }

        return RespBean.ok("success");
    }

    @GetMapping("/download")
    public RespBean downloadNotebooks(@RequestHeader String token){
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        return RespBean.ok("同步成功",notebookService.downloadNotebook(userInfo.getId()));
    }

    @GetMapping("/getDir")
    public RespBean getAllFilesDir(@RequestHeader String token) throws FileNotFoundException {
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        return RespBean.ok("success",notebookService.getAllDir(userInfo.getId()));
    }

    @GetMapping("/deleteAll")
    public RespBean deleteAll(@RequestHeader String token) throws FileNotFoundException {
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        File path = ResourceUtils.getFile("classpath:");
        File uploadDir = new File(path.getAbsolutePath(),"static/file");
        notebookService.deleteDirs(new File(uploadDir.getAbsolutePath() + "/" + userInfo.getId()));
        return RespBean.ok("删除成功");
    }

    @GetMapping("/test")
    public String test(String name){
        System.out.println(name);
        return name + "!!!";
    }
}
