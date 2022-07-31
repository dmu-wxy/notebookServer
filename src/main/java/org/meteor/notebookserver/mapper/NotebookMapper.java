package org.meteor.notebookserver.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.meteor.notebookserver.model.Notebook;

import java.util.List;

public interface NotebookMapper {
    @Insert({
            "<script>",
            "insert into notebook(title,content,lastChangeTime) values ",
            "<foreach collection='notebooks' separator',' item='notebook'>",
            "(#{notebook.title},#{notebook.content},#{notebook.lastChangeTime})",
            "</foreach>",
            "</script>"
    })
    public int insert(@Param("notebooks") List<Notebook> notebooks);

    @Select("select * from notebook n,user_notebook un where n.id = un.noteboot_id and un.user_id = #{userId}")
    public List<Notebook> queryMy(Long userId);
}
