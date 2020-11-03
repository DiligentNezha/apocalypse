package com.apocalypse.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/30
 */
@Controller
public class DingController {
    @RequestMapping("/to/ding")
    public String ding() {
        return "ding";
    }
}
