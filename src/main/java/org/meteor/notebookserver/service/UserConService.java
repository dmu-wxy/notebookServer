package org.meteor.notebookserver.service;


import lombok.extern.java.Log;
import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.mapper.UserConMapper;
import org.meteor.notebookserver.mapper.UserMapper;
import org.meteor.notebookserver.model.LoginResp;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.pojo.User;
import org.meteor.notebookserver.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConService {
    @Autowired
    private UserConMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserConService.class);

    public UserInfo getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    public RespBean login(User userInfo){
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        if(username == null || password == null){
            return RespBean.AUTH_ERROR("账号或密码为空");
        }
        UserInfo user = userMapper.getUserByUsername(username);
        if(user == null || !password.equals(user.getPassword())){
            return RespBean.AUTH_ERROR("账号或密码错误");
        }
        String jwt = JwtUtil.createJWT(user.getId().toString(), user.getUsername());
        int num = userMapper.getNotebookNumById(userInfo.getId());
        return RespBean.ok("登录成功",new LoginResp(jwt,num));
    }

    public RespBean register(User userInfo){

        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        logger.info(username);
        logger.info(password);
        if(username == null || password == null){
            return RespBean.AUTH_ERROR("账号或密码为空");
        }
        UserInfo user = userMapper.getUserByUsername(username);
        if(user != null){
            return RespBean.AUTH_ERROR("用户已存在");
        }
        Integer id = userMapper.register(userInfo);
        String jwt = JwtUtil.createJWT(String.valueOf(id),username);
        return RespBean.ok("注册成功",new LoginResp(jwt,0));
    }

}
