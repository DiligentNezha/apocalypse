package com.apocalypse.front.controller;

import com.apocalypse.front.ValidateModel;
import com.apocalypse.front.exception.FrontException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> save(@Validated ValidateModel validateModel) throws FrontException{
        Map<String, Object> map = new HashMap<>();
        map.put("hello", "world");
        map.put("model", validateModel);
        try {
            int i = 10 / 0;
        } catch (Exception e) {
            throw new FrontException("具体业务出错信息");
        }
        return map;
    }

}
