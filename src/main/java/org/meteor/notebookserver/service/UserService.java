package org.meteor.notebookserver.service;


import org.meteor.notebookserver.entity.UserInfo;
import org.meteor.notebookserver.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserInfo getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    public List<UserInfo> getAllUser(){
        return userMapper.getALlUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userMapper.getUserByUsername(username);
        if (userInfo == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        String role = userInfo.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE" + role));
        return new User(
                userInfo.getUsername(),
                userInfo.getPassword(),
                authorities
        );
    }
}