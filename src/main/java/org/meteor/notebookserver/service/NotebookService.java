package org.meteor.notebookserver.service;

import org.meteor.notebookserver.mapper.NotebookMapper;
import org.meteor.notebookserver.entity.Notebook;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class NotebookService {

    @Autowired
    private NotebookMapper notebookMapper;

    private Logger logger = LoggerFactory.getLogger(NotebookService.class);

    public RespBean updateNotebook(List<Notebook> notebooks, Long userId){
        notebookMapper.deleteNotebookByUserId(userId);
        if (notebooks.size() > 0){
            // 因为要覆盖，所以先删除之前的
            notebookMapper.saveNotebook(notebooks,userId);
//            notebookMapper.saveUserNotebook(notebooks,userId);
        }
        return RespBean.ok("上传成功",notebooks.size());
    }

    public RespPageBean downloadNotebook(Long userId){
        List<Notebook> notebooks = notebookMapper.queryMy(userId);
        RespPageBean bean = new RespPageBean();
        bean.setData(notebooks);
        bean.setTotal(Long.valueOf(notebooks.size()));
        return bean;
    }

    public void deleteDirs(File dir){
        File[] files = dir.listFiles();
        // 如果有文件需要先删除文件
        if (files != null && files.length != 0){
            for(File file : files){
                if(file.isFile()){
                    file.delete();
                } else {
                    deleteDirs(file);
                }
            }
        }
        dir.delete();
    }

    public List<String> getAllDir(Long id) throws FileNotFoundException {
//        File uploadDir = new File(ResourceUtils.getFile("classpath:").getAbsolutePath(),"static/file/" + id + "/notebook");
        File uploadDir = new File(getNotebookDir() + id + "/notebook/");
        File[] files = uploadDir.listFiles();
        if(files == null || files.length == 0) return new LinkedList<>();
        List<String> notebookList = new LinkedList<>();
        // /file/<userId>/notebook/<notebookId>/<fileName>
        for(File file : files){
            if(file.isFile()){
                // loading.gif
                continue;
            }
            // 笔记文件以及笔记文件中的图片
            File[] notebooks = file.listFiles();
            for(File notebook : notebooks){
                // 下载地址
                String url = "/" + id + "/notebook/" + file.getName() + "/" + notebook.getName();
                notebookList.add(url);
            }
        }
        return notebookList;
    }

    public String getNotebookDir(){
        ApplicationHome ah = new ApplicationHome(getClass());
        File uploadDir = ah.getSource().getParentFile();
        return uploadDir.getAbsolutePath() + "/file/";
    }
}
