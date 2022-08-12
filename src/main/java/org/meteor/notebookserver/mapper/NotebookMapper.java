package org.meteor.notebookserver.mapper;


import org.apache.ibatis.annotations.*;
import org.meteor.notebookserver.entity.Notebook;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface NotebookMapper {
    @Insert({
            "<script>",
            "insert into notebookinfo(id,title,content,lastChangeTime,createTime) values ",
            "<foreach collection='notebooks' separator=',' item='notebook'>",
            "(#{notebook.id},#{notebook.title},#{notebook.content},#{notebook.lastChangeTime},#{notebook.createTime})",
            "</foreach>",
            "</script>"
    })
    void saveNotebook(@Param("notebooks") List<Notebook> notebooks);

    @Insert({
            "<script>",
            "insert into user_notebook(userId,notebookId) values ",
            "<foreach collection='notebooks' separator=',' item='notebook'>",
            "(#{userId},#{notebook.id})",
            "</foreach>",
            "</script>"
    })
    void saveUserNotebook(List<Notebook> notebooks,Long userId);

    @Delete("delete from notebookinfo where id in (select notebookId from user_notebook where userId = #{userId})")
    void deleteNotebookByUserId(Long userId);

    @Select("select * from notebookinfo n,user_notebook un where n.id = un.notebookId and un.userId = #{userId}")
    public List<Notebook> queryMy(Long userId);

}
