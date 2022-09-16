package com.ssfw.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 首页 前端控制器
 * @author a
 * @date 2022/9/15
 * @since 2.7.3
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * 首页
     * @param model Model
     * @return /index.html
     */
    @GetMapping()
    public String index(Model model){
        model.addAttribute("nickname","南霸天");
        return "/index.html";
    }

    /**
     * 登录页
     * @param model Model
     * @return
     */
    @GetMapping("login")
    public String login(Model model){
        return "/login.html";
    }
}
