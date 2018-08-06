package com.ljy.spring.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:MethodMapping
 * @Description: 映射方法的bean
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class MethodMapper {

    private String className;

    private String methodName;

    private List<Class> paramTypes = new ArrayList<>();

    private List<String> paramNames;

    public MethodMapper(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public List<String> getParamNames() {
        return paramNames;
    }

    public void setParamNames(List<String> paramNames) {
        this.paramNames = paramNames;
    }

    public List<Class> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<Class> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

}
