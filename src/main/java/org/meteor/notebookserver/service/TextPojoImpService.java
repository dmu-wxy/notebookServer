package org.meteor.notebookserver.service;

import org.meteor.notebookserver.controller.TextPojoController;
import org.meteor.notebookserver.mapper.TextPojoImpMapper;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.model.RespPageBean;
import org.meteor.notebookserver.pojo.TextPojoImp;
import org.meteor.notebookserver.pojo.TextPojoImpCon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.logging.Logger;

@Service
public class TextPojoImpService {
    @Autowired
    private TextPojoImpMapper textPojoImpMapper;

    public RespBean updateTextPojo(List<TextPojoImp> textPojoImps,Long userId) {
        textPojoImpMapper.deleteNotebookByUserId(userId);



        if(textPojoImps.size()>0){

            for (TextPojoImp textPojoImp:textPojoImps)
            {
                textPojoImp.setUserId(userId);
            }
            textPojoImpMapper.saveTextPojo(textPojoImps);

        }
        return RespBean.ok("上传成功",textPojoImps.size());
    }

    public RespPageBean getAll(Long userId){
        List<TextPojoImp> textPojoImps = textPojoImpMapper.queryByUserId(userId);
        RespPageBean bean = new RespPageBean();
        List<TextPojoImpCon> textPojoImpCons = new ArrayList<>();
        for (TextPojoImp textPojoImp :textPojoImps){
            TextPojoImpCon textPojoImpCon = new TextPojoImpCon();
            textPojoImpCon.setTextContent(textPojoImp.getTextContent());
            textPojoImpCon.setTextSpan(textPojoImp.getTextSpan());
            textPojoImpCon.setId(textPojoImp.getId());
            textPojoImpCon.setTag(textPojoImp.getTag());
            textPojoImpCon.setTime(textPojoImp.getTime());
            textPojoImpCon.setHeadlineContent(textPojoImp.getHeadlineContent());
            textPojoImpCon.setImgSrc(textPojoImp.getImgSrc());
            textPojoImpCon.setHeadlineSpan(textPojoImp.getHeadlineSpan());
            textPojoImpCons.add(textPojoImpCon);
        }
        bean.setData(textPojoImpCons);
        bean.setTotal(Long.valueOf(textPojoImpCons.size()));
        return bean;
    }

    public RespPageBean remove(Long id,Long userId) {
        textPojoImpMapper.removeUserNote(id,userId);
        List<TextPojoImp> textPojoImps = textPojoImpMapper.queryMy(userId);
        RespPageBean bean = new RespPageBean();
        bean.setData(textPojoImps);
        bean.setTotal(Long.valueOf(textPojoImps.size()));
        return bean;
    }
}
