package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYAop;
import com.ljy.spring.aop.Aop;
import com.ljy.spring.bean.BeanContainer;
import com.ljy.spring.proxy.SonProxy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @ClassName:AopProxy
 * @Description:
 * @Author: ljy
 * @Date: 2018/8/3
 **/
public class AopProxyInstantiates {

    public static void instan() {
        for (Object obj : BeanContainer.beanMapping.values()) {
            if (obj.getClass().isAnnotationPresent(LJYAop.class)){
                process(obj, null);
            } else {
                Method[] methods = obj.getClass().getMethods();
                for (final Method method : methods) {
                    if (method.isAnnotationPresent(LJYAop.class)) {
                        process(obj, method);
                    }
                }
            }
        }
        Map<String, Object> aopProxyBeanMapping = BeanContainer.aopProxyBeanMapping;
        System.out.println(aopProxyBeanMapping);
    }

    private static void process(final Object obj, final Method method) {
        LJYAop aopAnno = null;
        if (method != null) aopAnno = method.getAnnotation(LJYAop.class);
        else aopAnno = obj.getClass().getAnnotation(LJYAop.class);
        String aopBeanName = aopAnno.aopBeanName();
        final String aopType = aopAnno.aopType();
        final Aop aopBean = (Aop) BeanContainer.beanMapping
                .get(BeanContainer.aopNameClassMapping.get(aopBeanName));
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
        BeanContainer.aopProxyBeanMapping.put(obj.getClass().getName(), proxy);
    }

    /*
    private static boolean hasInterface(Class<?> aClass) {
        Class<?>[] interfaces = aClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) return true;
        if (aClass.getSuperclass() != null) return hasInterface(aClass.getSuperclass());
        return false;
    }*/

    public static void main(String[] args) {
        String a = "a";
        Object b = a;
        Class clazz = b.getClass();
    }

}
