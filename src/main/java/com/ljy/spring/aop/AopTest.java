package com.ljy.spring.aop;

import com.ljy.spring.annotation.LJYAopBean;

/**
 * @ClassName:AopTest
 * @Description:测试aop类
 * @Author: ljy
 * @Date: 2018/8/3
 **/
@LJYAopBean("aop.test")
public class AopTest implements Aop<String> {

    @Override
    public void pro() {
        System.out.println("aop前");
    }

    @Override
    public String after(String obj) {
        return obj + "---aop后";
    }


}
