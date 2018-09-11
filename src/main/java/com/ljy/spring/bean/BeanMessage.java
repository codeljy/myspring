package com.ljy.spring.bean;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @ClassName:BeanMessage
 * @Description:
 * @Author: ljy
 * @Date: 2018/8/30
 **/
public class BeanMessage {

    public static final String CONTROLLER = "controller";
    public static final String SERVICE = "service";
    public static final String AOP = "aop";

    // 别名
    private String name;

    // 类名
    private String className;

    // bean的类型
    private String beanType;

    // aop对象
    private Object aop;

    // 对象
    private Object bean;

    private List<MethodMessage> methods;

    public String getBeanType() {
        return beanType;
    }

    public void setBeanType(String beanType) {
        this.beanType = beanType;
    }

    public List<MethodMessage> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodMessage> methods) {
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getAop() {
        return aop;
    }

    public void setAop(Object aop) {
        this.aop = aop;
    }

    public Object gainInvokeObj() {
        return aop == null ? bean : aop;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public BeanMessage gainBeanMessage(String targe){
        if (name != null && name.equals(targe)) return this;
        if (className != null && className.equals(targe)) return this;
        return null;
    }

    public Object gainObject() {
        return aop == null ? bean : aop;
    }

    public boolean isController() {
        return CONTROLLER.equals(this.beanType);
    }

    public boolean isService() {
        return SERVICE.equals(this.beanType);
    }

    public boolean isAop() {
        return AOP.equals(this.beanType);
    }

    public MethodMessage gainMethod(String methodName) {
        for (MethodMessage m : methods) {
            if (m.getMethodName().equals(methodName)) return m;
        }
        return null;
    }

    public static class MethodMessage{

        private List<Class> paramTypes;

        private List<String> paramNames;

        private String methodName;

        private Method method;

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public List<Class> getParamTypes() {
            return paramTypes;
        }

        public void setParamTypes(List<Class> paramTypes) {
            this.paramTypes = paramTypes;
        }

        public List<String> getParamNames() {
            return paramNames;
        }

        public void setParamNames(List<String> paramNames) {
            this.paramNames = paramNames;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
    }

}
