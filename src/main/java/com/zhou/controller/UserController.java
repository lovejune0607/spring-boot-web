package com.zhou.controller;


import com.zhou.entity.User1;
import com.zhou.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;


    @RequestMapping(value="/user")
    public List<User1> userList(){
        return userRepository.findAll();
    }

}
