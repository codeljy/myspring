package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYAutowired;
import com.ljy.spring.annotation.LJYResource;
import com.ljy.spring.bean.BeanMessage;
import com.ljy.spring.factory.BeanFactory;
import java.lang.reflect.Field;

/**
 * @ClassName:ParamInsert
 * @Description:对象注入
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class ParamInsert {

    /**
     * @author: ljy
     * @date: 2018/9/5
     * @description: 为对象的成员变量注入对象
     */
    public static void insert() {
        try {
            for (BeanMessage beanMessage : BeanFactory.instance().getBeans()) {
                Object obj = beanMessage.getBean();
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
                                beanInsert(obj, f, n);
                            } else {
                                beanInsert(obj, f, f.getType().getName());
                            }
                        } else {
                            beanInsert(obj, f, value);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: ljy
     * @date: 2018/9/5
     * @description: 反射注入变量
     */
    private static void beanInsert(Object obj, Field f, String name)
            throws IllegalAccessException {
        BeanMessage beanMessage = BeanFactory.instance().gainBean(name);
        f.set(obj, beanMessage.gainObject());
    }

}
