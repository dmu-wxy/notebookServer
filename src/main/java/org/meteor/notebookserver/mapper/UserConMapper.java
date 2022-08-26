package org.meteor.notebookserver.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.pojo.User;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserConMapper {
    @Select("select * from user where username = #{username}")
    UserInfo getUserByUsername(String username);

    @Select("select count(*) from textpojoimp where userId = #{id}")
    Integer getNotebookNumById(Long id);
    @Insert("insert into user(username,password) values (#{username},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer register( User userInfo);
}
