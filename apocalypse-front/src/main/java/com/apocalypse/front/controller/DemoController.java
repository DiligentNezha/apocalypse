package com.apocalypse.front.controller;

import com.apocalypse.front.ValidateModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, String> save(@Validated ValidateModel validateModel) {
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");
        return map;
    }

}
