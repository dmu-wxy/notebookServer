package org.meteor.notebookserver.controller;

import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return user;
    }

    @PostMapping("/login")
    public RespBean login(@RequestBody UserInfo userInfo){
        RespBean login = userService.login(userInfo);
        return login;
    }

    @PostMapping("/register")
    public RespBean register(@RequestBody UserInfo userInfo){
        return userService.register(userInfo);
    }

    @GetMapping("/getAllUsers")
    public List<UserInfo> getAllUsers(String adminPass){
        if(!"Meteor123.".equals(adminPass)) return null;
        return userService.getAllUsers();
    }

    @GetMapping("/delete")
    public boolean deleteUserById(Long id,String adminPass){
        if(!"Meteor123.".equals(adminPass)) return false;
        return userService.delete(id);
    }
}
