package com.boot.controller;

//请求进来,先去找Controller看能不能处理.不能处理的所有请求又都交给静态资源处理器.静态资源也找不到则响应404页面
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @RequestMapping("/2.png")
    public String test(){
        return "是控制器处理的这个请求,而不是静态资源处理器";
    }


//  @RequestMapping(value = "/user",method = RequestMethod.GET)
    @GetMapping("/user")
    public String getUser(){
        return "GET-张三";
    }

//  @RequestMapping(value = "/user",method = RequestMethod.POST)
    @PostMapping("/user")
    public String saveUser(){
        return "POST-张三";
    }

//  @RequestMapping(value = "/user",method = RequestMethod.PUT)
    @PutMapping("/user")
    public String putUser(){
        return "PUT-张三";
    }

//  @RequestMapping(value = "/user",method = RequestMethod.DELETE)
    @DeleteMapping("/user")
    public String deleteUser(){
        return "DELETE-张三";
    }


}
