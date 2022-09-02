package org.meteor.notebookserver.service;


import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.mapper.NotebookMapper;
import org.meteor.notebookserver.mapper.UserMapper;
import org.meteor.notebookserver.model.LoginResp;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotebookMapper notebookMapper;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserInfo getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    public RespBean login(UserInfo userInfo){
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
        Integer num = userMapper.getNotebookNumById(user.getId());
        return RespBean.ok("登录成功",new LoginResp(jwt,num));
    }

    public RespBean register(UserInfo userInfo){
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        if(username == null || password == null){
            return RespBean.AUTH_ERROR("账号或密码为空");
        }
        UserInfo user = userMapper.getUserByUsername(username);
        if(user != null){
            return RespBean.AUTH_ERROR("用户已存在");
        }
        userMapper.saveUser(userInfo);
        logger.info("" + userInfo.getId());
        String jwt = JwtUtil.createJWT(String.valueOf(userInfo.getId()),username);
        return RespBean.ok("注册成功",new LoginResp(jwt,0));
    }

    public List<UserInfo> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public boolean delete(Long id) {
        notebookMapper.deleteNotebookByUserId(id);
        userMapper.deleteUser(id);
        return true;
    }
}
