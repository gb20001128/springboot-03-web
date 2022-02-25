package com.boot.controller;

import com.boot.bean.Person;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ParameterTestController {


    //测试获取请求的注解
    @GetMapping("/car/{id}/owner/{username}")
    public Map<String,Object> getCar(@PathVariable("id") Integer id,                   //Rest风格注解,获取id参数
                                     @PathVariable("username") String name,            //Rest风格注解,获取username参数
                                     @PathVariable Map<String,String> pv,              //Rest风格注解,在参数这使用Map,可以将各个Rest参数值封装进Map集合
                                     @RequestHeader("User-Agent") String userAgent,   //获取头部信息里的User-Agent的信息
                                     @RequestHeader Map<String,String> header,        //获取所有头部信息
                                     @RequestParam("age") Integer age,                //普通参数注解注解,获取age参数
                                     @RequestParam("inters")List<String> inters,      //普通参数注解注解,同名为inters的参数可以放进一个List集合中
                                     @RequestParam Map<String,String> params,         //普通参数注解注解,在参数这使用Map,可以将各个普通参数值封装进Map集合
                                     @CookieValue("Idea-904df4b3") String Idea,       //获取头部cookie中Idea-904df4b3的值
                                     @CookieValue("Idea-904df4b3") Cookie cookie){    //通过cookie名字,取得整个cookie
        Map <String,Object> map=new HashMap<>();
//        map.put("id",id); 因为map存太多,看不清晰,就注了一些
//        map.put("name",name);
//        map.put("pv",pv);
//        map.put("User-Agent",userAgent);
//        map.put("Header",header);
        map.put("age",age);
        map.put("inters",inters);
        map.put("params",params);
        map.put("Idea-904df4b3",Idea);
        System.out.println(cookie.getName()+"===>"+cookie.getValue());
        return map;
    }


    //测试RequestBody注解
    @PostMapping("/save")
    public Map postMethod(@RequestBody String content){
        Map<String,Object> map = new HashMap<>();
        map.put("content",content);
        return map;
    }


    //1、语法: 请求路径:/cars/sell;low=34;brand=byd,audi,yd
    //2、SpringBoot默认是禁用了矩阵变量的功能
    //   手动开启:原理 对于路径的处理.UrlPathHelper进行解析.
    //              removeSemicolonContent（移除分号内容）支持矩阵变量的
    //3、矩阵变量必须有url路径变量才能被解析
    @GetMapping("/cars/{path}")
    public Map carsSell(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> brand,
                        @PathVariable("path") String path){
        Map<String,Object> map = new HashMap<>();

        map.put("low",low);
        map.put("brand",brand);
        map.put("path",path);
        return map;
    }


    // 请求url: /boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossId}/{empId}")
    public Map boss(@MatrixVariable(value = "age",pathVar = "bossId") Integer bossAge,
                    @MatrixVariable(value = "age",pathVar = "empId") Integer empAge){
        /*上面意思:bossId路径的矩阵变量的age变量赋给bossAge,empId路径的矩阵变量的age变量赋给empAge*/
        Map<String,Object> map = new HashMap<>();

        map.put("bossAge",bossAge);
        map.put("empAge",empAge);
        return map;

    }

    //测试表单请求的各个name属性值,会封装到Person对象中(只能是GET或POST请求)
    @PostMapping("/saveuser")
    public Person saveuser(Person person){
        return person;
    }

}
