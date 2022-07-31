package org.meteor.notebookserver.service;

import org.meteor.notebookserver.mapper.NotebookMapper;
import org.meteor.notebookserver.model.Notebook;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotebookServiceImpl {

    @Autowired
    private NotebookMapper notebookMapper;

    public RespBean updateNotebook(List<Notebook> notebooks,Long userId){
        notebookMapper.insert(notebooks);
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
