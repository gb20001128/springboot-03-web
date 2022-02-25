package com.boot.controller;

import com.boot.bean.Person;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
@RestController
public class ResponseController {


    @ResponseBody //RequestResponseMethodProcessor --->messageConver
    @GetMapping("/he11")
    public FileSystemResource file(){
        //文件以这样的方式返回看是谁处理的(messageConverter)
        return null;
    }

/**
 * 改变浏览器响应的数据格式: 1.通过设置参数format的值  2.通过设置请求头里的Accept字段值
 *   1.浏览器发请求直接,返回xml            [application/xml]    jacksonXmlConverter
 *   2.如果是ajax请求,返回json            [application/json]   jacksonJsonConverter
 *   3.如果是此应用发请求,返回自定义协议数据  [application/xxx]    xxxxConverter
 *       格式:属性值1;属性值2
 *       步骤:
 *         1.添加自定义的MessageConverter进系统底层(在WebConfig里改)
 *         2.系统底层就会统计出所以MessageConverter能操作哪些类型
 *         3.客户端内容协商
 *  拓展:postman是做web页面开发和测试的用于发送 HTTP 请求的 Chrome插件(它可以改变Accept字段值,而浏览器不行)
**/
    @ResponseBody //利用返回值处理器里的消息转换器进行处理,能响应数据,让返回值直接返回到浏览器,而不是响应页面
    @GetMapping("/test/person")
     public Person getPerson(){
        Person person=new Person();
        person.setAge(28);
        person.setBirth(new Date());
        person.setUserName("王五");
        return person;
     }
}
