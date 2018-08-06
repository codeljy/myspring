package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYAutowired;
import com.ljy.spring.annotation.LJYResource;
import com.ljy.spring.bean.BeanContainer;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * @ClassName:ParamInsert
 * @Description:对象注入
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class ParamInsert {

    public static void insert() {
        try {
            Collection<Object> objects = BeanContainer.beanMapping.values();
            for (Object obj : objects) {
                Class<?> aClass = obj.getClass();
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.isAnnotationPresent(LJYAutowired.class)) {
                        beanInsert(obj, f, f.getType().getName());
                    } else if (f.isAnnotationPresent(LJYResource.class)) {
                        LJYResource resource = f.getDeclaredAnnotation(LJYResource.class);
                        String value = resource.value();
                        if (value == null || "".equals(value)) {
                            String n = resource.name();
                            if (n == null || "".equals(n)) {
                                Class<?> type = f.getType();
                                beanInsert(obj, f, BeanContainer.nameClassMapping.get(value));
                            } else {
                                beanInsert(obj, f, f.getType().getName());
                            }
                        } else {
                            beanInsert(obj, f, BeanContainer.nameClassMapping.get(value));
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void beanInsert(Object obj, Field f, String name)
            throws IllegalAccessException {
        Object aopObj = BeanContainer.aopProxyBeanMapping.get(name);
        if (aopObj != null) {
            f.set(obj, aopObj);
        } else {
            f.set(obj, BeanContainer.beanMapping.get(name));
        }
    }

}
