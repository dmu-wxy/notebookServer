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
            "insert into notebookinfo(id,title,abs,lastChangeTime,createTime,firstImageName,userId,label) values ",
            "<foreach collection='notebooks' separator=',' item='notebook'>",
            "(#{notebook.id},#{notebook.title},#{notebook.abs},#{notebook.lastChangeTime},#{notebook.createTime},#{notebook.firstImageName},#{userId},#{notebook.label})",
            "</foreach>",
            "</script>"
    })
    void saveNotebook(@Param("notebooks") List<Notebook> notebooks,@Param("userId")Long userId);

    @Insert({
            "<script>",
            "insert into user_notebook(userId,notebookId) values ",
            "<foreach collection='notebooks' separator=',' item='notebook'>",
            "(#{userId},#{notebook.id})",
            "</foreach>",
            "</script>"
    })
    void saveUserNotebook(List<Notebook> notebooks,Long userId);

    @Delete("delete from notebookinfo where userId = #{userId}")
    void deleteNotebookByUserId(Long userId);

    @Select("select * from notebookinfo where userId = #{userId}")
    public List<Notebook> queryMy(Long userId);

}
