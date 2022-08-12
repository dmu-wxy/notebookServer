package org.meteor.notebookserver.service;

import org.meteor.notebookserver.mapper.NotebookMapper;
import org.meteor.notebookserver.entity.Notebook;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if (notebooks.size() > 0){
            // 因为要覆盖，所以先删除之前的
            notebookMapper.deleteNotebookByUserId(userId);
            notebookMapper.saveNotebook(notebooks);
            notebookMapper.saveUserNotebook(notebooks,userId);
        }
        return RespBean.ok("上传成功");
    }

    public RespPageBean downloadNotebook(Long userId){
        List<Notebook> notebooks = notebookMapper.queryMy(userId);
        RespPageBean bean = new RespPageBean();
        bean.setData(notebooks);
        bean.setTotal(Long.valueOf(notebooks.size()));
        return bean;
    }
}
