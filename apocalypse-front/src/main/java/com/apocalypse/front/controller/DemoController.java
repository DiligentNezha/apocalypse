package com.apocalypse.front.controller;

import cn.hutool.core.util.RandomUtil;
import com.apocalypse.common.dto.JsonResult;
import com.apocalypse.common.exception.ControllerException;
import com.apocalypse.example.model.UserModel;
import com.apocalypse.front.ValidateModel;
import com.apocalypse.front.biz.UserBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private UserBusiness userBusiness;

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public JsonResult<ValidateModel> save(@RequestBody @Validated ValidateModel validateModel) throws ControllerException {
        JsonResult<ValidateModel> objectJsonResult = new JsonResult<>();
        objectJsonResult.setData(validateModel);
        objectJsonResult.setCode("20002");
        objectJsonResult.setMsg("save success");
        objectJsonResult.setSuccess(true);
        return objectJsonResult;
    }

    @ResponseBody
    @GetMapping(value = "/user/{userId}")
    public UserModel user(@PathVariable("userId") String userId) throws ControllerException{
        UserModel user = null;
        try {
            user = userBusiness.getUser(userId);
            //处理业务时，预知到的异常
            if (RandomUtil.randomInt() % 2 == 0) {
                logger.warn("Controller 层具体业务异常描述信息（此处会记录在日志中）");
                throw new ControllerException("250002", "Controller 具体业务异常描述（此处会返回给前端）");
            }
            //处理业务时，抛出可能未预知到的异常
            int i = 10 / RandomUtil.randomInt(3);
        } catch (Exception e) {
            String template = "Business 获取用户异常（此处会记录在日志中）用户编号:{}，用户姓名:{}";
            throw new ControllerException(logger, "150002", "Controller 获取用户异常（此处会返回给前端）", e, template, "1021212",
                    "小哪吒");
        }
        return user;
    }
}
