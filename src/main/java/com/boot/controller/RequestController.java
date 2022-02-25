package com.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RequestController {

    //测试一次请求转发中的参数值可以怎样获得
   @GetMapping("/goto")
    public String goToPage(HttpServletRequest request){
        request.setAttribute("msg","成功了...");
        return "forward:/success";  //转发到  /success请求
    }

    //测试形参的Map集合的数据和Model的数据是放在请求域中的,与设置请求域中的参数效果相同
    @GetMapping("/params")
    public String testParam(Map<String,Object> map,
                            Model model,
                            HttpServletRequest request,
                            HttpServletResponse response){
       map.put("hello","world666");                                   //此Map的键值对被放进请求域中
       model.addAttribute("world","hello666");//此Model的参数键值被放进请求域中
       request.setAttribute("message","HelloWorld");             //设置请求域的参数
       Cookie cookie=new Cookie("v1","c1");
       response.addCookie(cookie);
       return "forward:/success";
    }


    @ResponseBody //将方法的返回值,以特定的格式写入到response的body区域,进而将数据返回给客户端.
    @GetMapping("/success")
    public Map success(@RequestAttribute(value = "msg",required = false) String msg1,//false的意思是此请求参数可以没有
                       HttpServletRequest request){
         Object msg2=request.getAttribute("msg");
         Map <String,Object> map=new HashMap<>();
         Object hello=request.getAttribute("hello");     //在转发到的页面中也可以获得请求域中hello参数的值
         Object world=request.getAttribute("world");    //在转发到的页面中也可以获得请求域中world参数的值
         Object message=request.getAttribute("message");//在转发到的页面中也可以获得请求域中message参数的值

         map.put("reqMethod_msg",msg2); //请求转发算一次请求,通过HttpServletRequest对象的方法可以获得请求域中的参数值
         map.put("annotation_msg",msg1);//通过@RequestAttribute注解可以获得请求域中的参数值
         map.put("hello",hello);
         map.put("world",world);
         map.put("message",message);
         return map;
    }
}
