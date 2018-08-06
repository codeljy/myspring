package com.ljy.spring.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName:AopProxy
 * @Description:动态代理
 * @Author: ljy
 * @Date: 2018/8/3
 **/
public abstract class DynamicProxy {

    //维护一个目标对象
    private final Object target;
    public DynamicProxy(Object target){
        this.target=target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        preProcess();
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        afterProcess();
                        return returnValue;
                    }
                }
        );
    }

    public abstract void preProcess();

    public abstract void afterProcess();

}
