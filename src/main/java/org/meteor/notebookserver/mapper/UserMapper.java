package org.meteor.notebookserver.mapper;

import org.apache.ibatis.annotations.*;
import org.meteor.notebookserver.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    @Select("select * from userInfo where username = #{username}")
    UserInfo getUserByUsername(String username);

    @Select("select count(*) from notebookinfo where userId = #{id}")
    Integer getNotebookNumById(Long id);

    @Insert("insert into userInfo(username,password) values (#{username},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    Integer saveUser( UserInfo userInfo);

    @Select("select * from userInfo")
    List<UserInfo> getAllUsers();

    @Delete("delete from userInfo where id = #{id}")
    void deleteUser(Long id);
}
