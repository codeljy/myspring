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
            case "子羽":
                return "ljy:age-23,sex-mail";
            case "haha":
                return "泷泽萝拉:age-24,sex-femail";
            case "pei":
                return "波多野结衣:age-26,sex-femail";
            default:
                return "No Name:age-?,sex-?";
        }
    }

}
