package org.meteor.notebookserver.service;


import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.mapper.UserMapper;
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

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserInfo getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    public List<UserInfo> getAllUser(){
        return userMapper.getALlUser();
    }

}
