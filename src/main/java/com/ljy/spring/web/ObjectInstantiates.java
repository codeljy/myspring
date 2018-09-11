package com.ljy.spring.web;

import com.ljy.spring.annotation.LJYAopBean;
import com.ljy.spring.annotation.LJYContoller;
import com.ljy.spring.annotation.LJYService;
import com.ljy.spring.bean.BeanMessage;
import com.ljy.spring.bean.BeanMessage.MethodMessage;
import com.ljy.spring.factory.BeanFactory;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * @ClassName:ObjectInstantiates
 * @Description:对象实例化
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class ObjectInstantiates {



    /**
     * @author: ljy
     * @date: 2018/9/4
     * @description: 扫描类注解，进行对象实例化
     */
    public static void instan(List<Class> clazzs) {
        // 遍历类对象
        for (Class clazz : clazzs) {
            // 判断是否有符合条件的注解的标识
            boolean flag = true;
            // 创建BeanMessage对象用于存储对象信息
            BeanMessage bm = new BeanMessage();
            // controller对象
            if (clazz.isAnnotationPresent(LJYContoller.class)) {
                flag = true;
                LJYContoller contoller = (LJYContoller) clazz.getAnnotation(LJYContoller.class);
                String value = contoller.value();
                if (value != null && !"".equals(value)) {
                    addNameClass(value, clazz.getName(), bm, BeanMessage.CONTROLLER);
                } else {
                    String name = contoller.name();
                    if (name != null && !"".equals(name)) {
                        addNameClass(name, clazz.getName(), bm, BeanMessage.CONTROLLER);
                    }
                }
            }
            // service对象
            else if (clazz.isAnnotationPresent(LJYService.class)) {
                flag = true;
                LJYService service = (LJYService) clazz.getAnnotation(LJYService.class);
                String value = service.value();
                if (value != null && !"".equals(value)) {
                    addNameClass(value, clazz.getName(), bm, BeanMessage.SERVICE);
                } else {
                    String name = service.name();
                    if (name != null && !"".equals(name)) {
                        addNameClass(name, clazz.getName(), bm, BeanMessage.SERVICE);
                    }
                }
            }
            // aop对象
            else if (clazz.isAnnotationPresent(LJYAopBean.class)) {
                LJYAopBean aopBean = (LJYAopBean) clazz.getAnnotation(LJYAopBean.class);
                String value = aopBean.value();
                addNameClass(value, clazz.getName(), bm, BeanMessage.AOP);
            }
            // 不存在创建Bean的注解，则将标识设为false
            else {
                flag = false;
            }
            if (flag) {
                try {
                    // 反射生成对象
                    bm.setBean(clazz.newInstance());
                    // 获取当前对象的所有方法
                    Method[] methods = clazz.getDeclaredMethods();
                    // 创建methodMessages来存储方法信息
                    List<MethodMessage> methodMessages = new ArrayList<>();
                    // 遍历方法
                    for (Method m : methods) {
                        // 新建一个methodMessage对象
                        MethodMessage mm = new MethodMessage();
                        // 设置方法名
                        mm.setMethodName(m.getName());
                        // 设置入参名称
                        mm.setParamNames(getParamNames(m.getName(), clazz));
                        // 设置入参类型
                        mm.setParamTypes(Arrays.<Class>asList(m.getParameterTypes()));
                        // 设置方法对象
                        mm.setMethod(m);
                        // 添加MethodMessage到集合中
                        methodMessages.add(mm);
                    }
                    // 添加MethodMessage集合到BeanMessage中
                    bm.setMethods(methodMessages);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 将对象放进工厂
                BeanFactory.instance().addBean(bm);
            }
        }
    }

    /**
     * @author: ljy
     * @date: 2018/9/4
     * @description: 初始化BeanMessage对象的部分属性
     */
    private static void addNameClass(String name, String className, BeanMessage bm, String type) {
        bm.setName(name);
        bm.setClassName(className);
        bm.setBeanType(type);
    }

    /**
     * @author: ljy
     * @date: 2018/9/11
     * @description: 通过方法名和类对象，获取该方法的入参名称列表
     */
    private static List<String> getParamNames(String methodName, Class c) throws Exception {
        List<String> paramNames = new ArrayList<>();
        Class<?> clazz = c;
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get(clazz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);

            // 使用javassist的反射方法的参数名
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int len = ctMethod.getParameterTypes().length;
                // 非静态的成员函数的第一个参数是this
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                for (int i = 0; i < len; i++) {
                    paramNames.add(attr.variableName(i + pos));
                }
            }
            return paramNames;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
