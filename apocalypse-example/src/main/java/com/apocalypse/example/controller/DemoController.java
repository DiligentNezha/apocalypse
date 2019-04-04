package com.apocalypse.example.controller;

import com.apocalypse.example.model.UserModel;
import com.apocalypse.example.service.simple.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserModel user(@PathVariable("id") Integer id) {
        return userService.selectByPrimaryKey(id);
    }
}
