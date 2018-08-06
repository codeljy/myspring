package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYRequestMapping;
import com.ljy.spring.bean.BeanContainer;
import com.ljy.spring.bean.MethodMapper;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * @ClassName:UrlMethodMapper
 * @Description:url与方法映射
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class UrlMethodMapper {

    public static void readUrl() {
        Set<String> controllerSet = BeanContainer.controllerNameClassMapping.keySet();
        for (String key : controllerSet) {
            Object obj = BeanContainer.beanMapping
                    .get(BeanContainer.controllerNameClassMapping.get(key));
            if (obj.getClass().isAnnotationPresent(LJYRequestMapping.class)) {
                LJYRequestMapping beanUrlAnno = obj.getClass()
                        .getAnnotation(LJYRequestMapping.class);
                String beanUrl = beanUrlAnno.value();
                Method[] methods = obj.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(LJYRequestMapping.class)) {
                        LJYRequestMapping methodAnnotation = method
                                .getAnnotation(LJYRequestMapping.class);
                        String methodUrl = methodAnnotation.value();
                        String url = (beanUrl + methodUrl).replace("//", "/");
                        MethodMapper methodMapper = new MethodMapper(obj.getClass().getName(),
                                method.getName());
                        BeanContainer.urlMapping.put(url, methodMapper);
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        methodMapper.getParamTypes().addAll(Arrays.asList(parameterTypes));
                        System.out.println(methodMapper.getParamTypes());
                    }
                }
            }
        }
    }

}
