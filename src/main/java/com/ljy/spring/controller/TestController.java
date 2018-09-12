package com.ljy.spring.controller;


import com.ljy.spring.annotation.LJYContoller;
import com.ljy.spring.annotation.LJYRequestMapping;
import com.ljy.spring.annotation.LJYResource;
import com.ljy.spring.service.TestService;

/**
 * @ClassName:TestController
 * @Description:测试入口
 * @Author: ljy
 * @Date: 2018/7/27
 **/
@LJYContoller("test")
@LJYRequestMapping("/test")
public class TestController {

    @LJYResource("testService")
    private TestService testService;

    @LJYRequestMapping("/helloWorld")
    public String helloWorld(String name) {
        return testService.getPeople(name);
    }

}
