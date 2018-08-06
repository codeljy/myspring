package com.ljy.spring.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:BeanMap
 * @Description:
 * @Author: ljy
 * @Date: 2018/8/1
 **/
public class BeanContainer {

    // url与方法bean的映射
    public static Map<String, MethodMapper> urlMapping = new HashMap<>();

    // 所有bean的名称与类名映射
    public static Map<String, String> nameClassMapping = new HashMap<>();

    // controller的名称与类名映射
    public static Map<String, String> controllerNameClassMapping = new HashMap<>();

    // service的名称与类名映射
    public static Map<String, String> serviceNameClassMapping = new HashMap<>();

    // aop类的名称与类名映射
    public static Map<String, String> aopNameClassMapping = new HashMap<>();

    // 类名与bean的映射
    public static Map<String, Object> beanMapping = new HashMap<>();

    // aop代理对象，代理的类的类名与代理对象的映射，优先使用代理对象，代理对象为空才使用beanMapping的对象
    public static Map<String, Object> aopProxyBeanMapping = new HashMap<>();

}
