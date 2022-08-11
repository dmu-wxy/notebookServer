package org.meteor.notebookserver.controller;

import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/getUserInfo")
    public UserInfo getUser(String username){
        UserInfo user = userService.getUser(username);
        user = new UserInfo();
        return user;
    }

    @GetMapping("/getAll")
    public List<UserInfo> getAllUser(){
        return userService.getAllUser();
    }

}
