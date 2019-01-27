package com.apocalypse.front.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.apocalypse.common.constant.VersionConsant;
import com.apocalypse.common.exception.FrontException;
import com.apocalypse.example.dubbo.simple.DubboUserService;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.front.ValidateModel;
import com.apocalypse.front.biz.UserBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private UserBusiness userBusiness;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> save(@Validated ValidateModel validateModel) throws FrontException {
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

    @ResponseBody
    @GetMapping(value = "/user/{userId}")
    public UserModel user(@PathVariable("userId") String userId) throws FrontException{
        return userBusiness.getUser(userId);
    }
}
