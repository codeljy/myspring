package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYAopBean;
import com.ljy.spring.annotation.LJYContoller;
import com.ljy.spring.annotation.LJYService;
import com.ljy.spring.bean.BeanContainer;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:ObjectInstantiates
 * @Description:对象实例化
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class ObjectInstantiates {

    public static void instan(List<Class> clazzs) {
        for (Class clazz : clazzs) {
            boolean flag = false;
            if (clazz.isAnnotationPresent(LJYContoller.class)) {
                LJYContoller contoller = (LJYContoller) clazz.getAnnotation(LJYContoller.class);
                String value = contoller.value();
                if (value != null && !"".equals(value)) {
                    addNameClass(value, clazz.getName(), BeanContainer.controllerNameClassMapping);
                } else {
                    String name = contoller.name();
                    if (name != null && !"".equals(name)) {
                        addNameClass(name, clazz.getName(), BeanContainer.controllerNameClassMapping);
                    }
                }
                flag = true;
            } else if (clazz.isAnnotationPresent(LJYService.class)) {
                LJYService service = (LJYService) clazz.getAnnotation(LJYService.class);
                String value = service.value();
                if (value != null && !"".equals(value)) {
                    BeanContainer.serviceNameClassMapping.put(value, clazz.getName());
                    addNameClass(value, clazz.getName(), BeanContainer.serviceNameClassMapping);
                } else {
                    String name = service.name();
                    if (name != null && !"".equals(name)) {
                        addNameClass(name, clazz.getName(), BeanContainer.serviceNameClassMapping);
                    }
                }
                flag = true;
            } else if (clazz.isAnnotationPresent(LJYAopBean.class)) {
                LJYAopBean aopBean = (LJYAopBean) clazz.getAnnotation(LJYAopBean.class);
                String value = aopBean.value();
                addNameClass(value, clazz.getName(), BeanContainer.aopNameClassMapping);
                flag = true;
            }
            if (flag)
                try {
                    BeanContainer.beanMapping.put(clazz.getName(), clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
        }
    }

    private static void addNameClass(String name, String className, Map map) {
        map.put(name, className);
        BeanContainer.nameClassMapping.put(name, className);
    }
}
