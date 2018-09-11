package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYAop;
import com.ljy.spring.aop.Aop;
import com.ljy.spring.bean.BeanMessage;
import com.ljy.spring.factory.BeanFactory;
import com.ljy.spring.proxy.SonProxy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @ClassName:AopProxy
 * @Description:
 * @Author: ljy
 * @Date: 2018/8/3
 **/
public class AopProxyInstantiates {

    /**
     * @author: ljy
     * @date: 2018/9/4
     * @description: aop对象的实例化
     */
    public static void instan() {
        // 遍历所有的bean
        for (BeanMessage beanMessage : BeanFactory.instance().getBeans()) {
            Object obj = beanMessage.getBean();
            // 获取对象的所有方法
            Method[] methods = obj.getClass().getDeclaredMethods();
            // 遍历方法
            for (final Method method : methods) {
                // 若方法上有LJYAop注解，则进行aop对象的反向生成
                if (method.isAnnotationPresent(LJYAop.class)) {
                    process(obj, method);
                }
            }
            if (obj.getClass().isAnnotationPresent(LJYAop.class)){
                process(obj, null);
            }
        }
    }

    /**
     * @author: ljy
     * @date: 2018/9/4
     * @description: aop代理对象反向生成
     */
    private static void process(final Object obj, final Method method) {
        // 创建LJYAop对象
        LJYAop aopAnno = null;
        // 若是方法上的aop，则获取之，否则获取类上的aop
        if (method != null) aopAnno = method.getAnnotation(LJYAop.class);
        else aopAnno = obj.getClass().getAnnotation(LJYAop.class);
        // 获取aop的名称
        String aopBeanName = aopAnno.aopBeanName();
        // 获取aop的类型
        final String aopType = aopAnno.aopType();
        // 获取aop对象
        BeanMessage beanMessage = BeanFactory.instance().gainBean(aopBeanName);
        final Aop aopBean = (Aop) beanMessage.getBean();
        // 生成代理对象，使用子类代理方式
        Object proxy = new SonProxy(obj) {
            @Override
            public Object intercept(Object o, Method m, Object[] objects,
                    MethodProxy methodProxy)
                    throws Throwable {
                if (method != null && m.getName().equals(method.getName())) {
                    Object invoke = getObject(m, objects);
                    if (invoke != null) return invoke;
                } else {
                    Object invoke = getObject(m, objects);
                    if (invoke != null) return invoke;
                }
                return m.invoke(obj, objects);
            }

            private Object getObject(Method m, Object[] objects)
                    throws IllegalAccessException, InvocationTargetException {
                // 根据aop类型进行不同的执行顺序
                if (LJYAop.PRO.equals(aopType)) {
                    aopBean.pro();
                    return m.invoke(obj, objects);
                } else if (LJYAop.AFTER.equals(aopType)) {
                    Object invoke = m.invoke(obj, objects);
                    return aopBean.after(invoke);
                } else if (LJYAop.AROUND.equals(aopType)) {
                    aopBean.pro();
                    Object invoke = m.invoke(obj, objects);
                    return aopBean.after(invoke);
                }
                return null;
            }
        }.getProxy();
        // 设置代理对象
        BeanFactory.instance().gainBean(obj.getClass().getName()).setAop(proxy);
    }

}
