package com.hb.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author huangbing
 * @date 2020/03/25 22:03
 */
@Controller
@RequestMapping("/st/cs")
public class LogInController {
    @PostMapping("/signIn")
    public String logIn(String emailAddress, String password, Map<String, String> message, HttpSession session){
        if(StringUtils.equals(emailAddress, "hbtest@19.com") && StringUtils.equals(password, "123456")){
            session.setAttribute("loginInfo", emailAddress);
            //防止表单重复提交、重定向
            return "redirect:/manage";
        }
            //登录失败
            message.put("message", "用户名或密码错误");
            return "login";
    }
    @GetMapping("/test")
    public String test(){
        return "study";
    }

    @GetMapping("fastget")
    public String check(Model model){
        model.addAttribute("customize","false");
        return "study";
    }
}
