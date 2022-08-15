package org.meteor.notebookserver.service;

import org.meteor.notebookserver.mapper.NotebookMapper;
import org.meteor.notebookserver.entity.Notebook;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotebookService {

    @Autowired
    private NotebookMapper notebookMapper;

    private Logger logger = LoggerFactory.getLogger(NotebookService.class);

    public RespBean updateNotebook(List<Notebook> notebooks, Long userId){
        logger.info("userId: " + userId);
        logger.info("notebooks: " + notebooks);
        notebookMapper.deleteNotebookByUserId(userId);
        if (notebooks.size() > 0){
            // 因为要覆盖，所以先删除之前的
            notebookMapper.saveNotebook(notebooks);
            notebookMapper.saveUserNotebook(notebooks,userId);
        }
        return RespBean.ok("上传成功",notebooks.size());
    }

    public RespPageBean downloadNotebook(Long userId){
        List<Notebook> notebooks = notebookMapper.queryMy(userId);
        RespPageBean bean = new RespPageBean();
        logger.info("downloadNotebook notebooks: " + notebooks);
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
}
