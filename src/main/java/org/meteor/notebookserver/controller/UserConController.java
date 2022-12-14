package org.meteor.notebookserver.controller;

import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.model.RespBean;
import org.meteor.notebookserver.pojo.User;
import org.meteor.notebookserver.service.UserConService;
import org.meteor.notebookserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userCon")
public class UserConController {
    @Autowired
    private UserConService userService;

    Logger logger = LoggerFactory.getLogger(UserConController.class);

    @GetMapping("/getUserInfo")
    public UserInfo getUser(String username){
        UserInfo user = userService.getUser(username);
        return user;
    }

    @PostMapping("/login")
    public RespBean login(@RequestBody User userInfo){
        RespBean login = userService.login(userInfo);

        return login;
    }

    @PostMapping("/register")
    public RespBean register(@RequestBody User userInfo){
        return userService.register(userInfo);
    }
}
