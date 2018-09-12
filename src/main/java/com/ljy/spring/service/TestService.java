package com.ljy.spring.service;

import com.ljy.spring.annotation.LJYAop;
import com.ljy.spring.annotation.LJYService;

/**
 * @ClassName:TestService
 * @Description:测试业务类
 * @Author: ljy
 * @Date: 2018/7/27
 **/
@LJYService("testService")
public class TestService {

    @LJYAop(aopBeanName = "aop.test", aopType = LJYAop.AROUND)
    public String getPeople(String name) {
        if (name == null || "".equals(name)) return "No Name:age-?,sex-?";
        switch (name){
            case "ljy":
                return "Hello "+name+"!(age-23,sex-mail)";
            case "泷泽萝拉":
                return "Hello "+name+"!(age-24,sex-femail)";
            case "波多野结衣":
                return "Hello "+name+"!(age-27,sex-femail)";
            default:
                return "Who are you?";
        }
    }

}
