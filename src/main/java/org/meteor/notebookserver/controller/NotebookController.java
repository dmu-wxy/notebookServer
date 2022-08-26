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
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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
        return notebookService.updateNotebook(notebooks,userInfo.getId());
    }

    @PostMapping("/uploadFile")
    public RespBean updateNotebookFiles(MultipartFile notebook,@RequestHeader String token) throws IOException {
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        synchronized (NotebookController.class){
            String fileName = notebook.getOriginalFilename();
            // data\\user\\0\\org.meteor.notebook\\files".length()
            fileName = fileName.substring(38);
            String filePath = notebookService.getNotebookDir() + userInfo.getId();
            // 保存文件
            File saveFile = new File(filePath + "/" + fileName);
//            logger.info(saveFile.getPath());
            if(!saveFile.exists()){
                saveFile.mkdirs();
            }
            notebook.transferTo(saveFile);
        }

        return RespBean.ok("success");
    }

    @GetMapping("/downloadNotebooks")
    public void downloadNotebooks(String path, @RequestHeader String token, HttpServletResponse response) throws Exception {
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return;
        }
        File file = new File(notebookService.getNotebookDir() + path);
        String filename = file.getName();
        response.reset();
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        InputStream inputStream = new FileInputStream(file);

        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }

    @GetMapping("/download")
    public RespBean download(@RequestHeader String token){
        UserInfo userInfo = JwtUtil.geTokenInfo(token);
        if(userInfo == null) {
            return RespBean.AUTH_ERROR("未登录");
        }
        return RespBean.ok("success",notebookService.downloadNotebook(userInfo.getId()));
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
        File uploadDir = new File(notebookService.getNotebookDir());
        notebookService.deleteDirs(new File(uploadDir.getAbsolutePath() + "/" + userInfo.getId()));
        return RespBean.ok("删除成功");
    }

    @GetMapping("/test")
    public String test(String name){
        System.out.println(name);
        return name + "!!!";
    }
}
