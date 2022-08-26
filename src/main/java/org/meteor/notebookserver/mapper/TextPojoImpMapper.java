package org.meteor.notebookserver.mapper;

import org.apache.ibatis.annotations.*;
import org.meteor.notebookserver.entity.Notebook;
import org.meteor.notebookserver.pojo.TextPojoImp;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TextPojoImpMapper {
    @Insert({
            "<script>",
            "insert into textpojoimp(id, userId , headlineContent, headlineSpan, textSpan, textContent, `time`, imgSrc, tag) values ",
            "<foreach collection='textPojoImps' separator=',' item='textpojo'>",
            "(#{textpojo.id},#{textpojo.userId},#{textpojo.headlineContent},#{textpojo.headlineSpan},#{textpojo.textSpan},#{textpojo.textContent},#{textpojo.time},#{textpojo.imgSrc},#{textpojo.tag})",
            "</foreach>",
            "</script>"
    })
    void saveTextPojo(List<TextPojoImp> textPojoImps);

    @Delete("delete from textpojoimp where id = #{textid} ")
    void deleteTextPojoById(Long textid);

    @Insert({
            "<script>",
            "insert into user_textpojo(userId,notebookId) values ",
            "<foreach collection='textPojoImps' separator=',' item='textpojo'>",
            "(#{userId},#{textpojo.id})",
            "</foreach>",
            "</script>"
    })
    void saveUserTextPojo(List<TextPojoImp> textPojoImps,Long userId);


    @Select("select * from textpojoimp")
    public List<TextPojoImp> queryAll();

    @Delete("delete from  textpojoimp")
    void deleteAll();

    @Delete("delete from textpojoimp where userId = #{userId}")
    void deleteNotebookByUserId(Long userId);

    @Select("select * from textpojoimp n,user_textpojo un where n.id = un.notebookId and un.userId = #{userId}")
    public List<TextPojoImp> queryMy(Long userId);

    @Delete("delete from user_textpojo where userId = #{userId}")
    public void deleteUserTextPojo(Long userId);


    @Delete("delete from textpojoimp where id = #{textid}")
    public void remove(Long textid);

    @Delete("delete from user_textpojo where notebookId = #{textid} and userId = #{userId}")
    public void removeUserNote(Long textid,Long userId);

    @Select("select * from textpojoimp where userId = #{userId}")
    public List<TextPojoImp> queryByUserId(Long userId);
}
