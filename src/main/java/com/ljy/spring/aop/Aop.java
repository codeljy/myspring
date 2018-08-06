package com.ljy.spring.aop;

/**
 * Created by Administrator on 2018/8/3.
 */
public interface Aop<T> {

    public abstract void pro();

    public abstract T after(T obj);

}
