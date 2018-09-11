package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYRequestMapping;
import com.ljy.spring.bean.BeanMessage;
import com.ljy.spring.bean.UrlMethodRelate;
import com.ljy.spring.factory.BeanFactory;
import com.ljy.spring.factory.UrlFactory;
import java.lang.reflect.Method;

/**
 * @ClassName:UrlMethodMapper
 * @Description:url与方法映射
 * @Author: ljy.
 * @Date: 2018/7/27
 **/
public class UrlMethodMapper {

    /**
     * @author: ljy
     * @date: 2018/9/4
     * @description: 建立url与方法的映射关系
     */
    public static void readUrl() {
        for (BeanMessage beanMessage : BeanFactory.instance().gainControllers()) {
            Object obj = beanMessage.getBean();
            if (obj.getClass().isAnnotationPresent(LJYRequestMapping.class)) {
                // 获取url注解
                LJYRequestMapping beanUrlAnno = obj.getClass()
                        .getAnnotation(LJYRequestMapping.class);
                // 获取类上的url注解
                String beanUrl = beanUrlAnno.value();
                urlRelateMethodProcess(obj, beanUrl);
            } else {
                urlRelateMethodProcess(obj, "");
            }
        }
    }

    private static void urlRelateMethodProcess(Object obj, String beanUrl) {
        // 获取方法
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(LJYRequestMapping.class)) {
                // 获取方法上的url注解
                LJYRequestMapping methodAnnotation = method
                        .getAnnotation(LJYRequestMapping.class);
                String methodUrl = methodAnnotation.value();
                // 组合最终的url
                String url = (beanUrl + methodUrl).replace("//", "/");
                // 保存url与方法的映射关系
                UrlMethodRelate urlMethodRelate = new UrlMethodRelate();
                urlMethodRelate.setUrl(url);
                urlMethodRelate.setClassName(obj.getClass().getName());
                urlMethodRelate.setMethodName(method.getName());
                UrlFactory.instance().addUrlMethodRelate(urlMethodRelate);
            }
        }
    }

}
