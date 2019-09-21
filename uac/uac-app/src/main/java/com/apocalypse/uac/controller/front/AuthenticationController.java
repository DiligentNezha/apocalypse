package com.apocalypse.uac.controller.front;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/20
 */
@Slf4j
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @GetMapping("/login/page")
    public String loginPage() {
        log.info("跳转登录页");
        return "../public/my-login";
    }

    @RequestMapping("/login/success")
    public String loginSuccess() {
        log.info("登录成功");
        // 重定向，同登录失败处理
        return "redirect:/home";
    }

    // 不能使用 GetMapping， 登录失败后 spring-security 会已 post 请求的方式转发到该路径
    @RequestMapping("/login/fail")
    public String loginFail() {
        log.info("登录失败");
        // 此时 spring-security
        // 重定向到登录失败页，不然路径会是处理登录表单提交的路径
        return "redirect:/login-fail";
    }
}
