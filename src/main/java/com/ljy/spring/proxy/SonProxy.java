package com.ljy.spring.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @ClassName:SonProxy
 * @Description:子类代理
 * @Author: ljy
 * @Date: 2018/8/3
 **/
public abstract class SonProxy implements MethodInterceptor {

    protected Object obj;

    public SonProxy(Object obj) {
        this.obj = obj;
    }

    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

}
