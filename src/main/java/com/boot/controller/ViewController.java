package com.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/tt")
    public String tt(Model model){
        //model的数据会被放在请求域中,相对于request.setAttribute("msg","你好")
        model.addAttribute("msg","你好");
        model.addAttribute("link","https://www.baidu.com");
        return "success";
    }
}
