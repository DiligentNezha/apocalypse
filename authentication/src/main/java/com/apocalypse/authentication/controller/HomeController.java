package com.apocalypse.authentication.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/19
 */
@Slf4j
@Controller
public class HomeController {

    @GetMapping(path = {"/home"})
    public String home() {
        log.info("登录成功！进入首页");
        return "home";
    }

}
