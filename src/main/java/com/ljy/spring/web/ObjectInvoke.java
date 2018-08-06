package com.ljy.spring.web;

import com.ljy.spring.bean.BeanContainer;
import com.ljy.spring.bean.MethodMapper;
import com.ljy.spring.controller.TestController;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName:ObjectInvoke
 * @Description:对象方法执行类
 * @Author: ljy
 * @Date: 2018/8/2
 **/
public class ObjectInvoke {

    public static String invoke(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String uri = req.getRequestURI().toString();
            if(uri.lastIndexOf("?") != -1) uri = uri.substring(0, uri.lastIndexOf("?"));
            MethodMapper methodMapper = BeanContainer.urlMapping.get(uri);
            if (methodMapper == null) return "404";
            Object obj = BeanContainer.beanMapping.get(methodMapper.getClassName());
            String methodName = methodMapper.getMethodName();
            List<Class> paramTypes = methodMapper.getParamTypes();
            Method method = obj.getClass().getDeclaredMethod(methodMapper.getMethodName(), paramTypes.toArray(new Class[methodMapper.getParamTypes().size()]));
            List<String> paramNames = null;
            if (methodMapper.getParamNames() == null || methodMapper.getParamNames().size() <= 0) {
                paramNames = getParamNames(methodName);
                methodMapper.setParamNames(paramNames);
            } else {
                paramNames = methodMapper.getParamNames();
            }
            System.out.println("方法的参数名称列表：" + paramNames);
            Map<String, Object> params = new LinkedHashMap<>();
            for (String paramName : paramNames) {
                params.put(paramName, req.getParameter(paramName));
                if (paramTypes.get(paramNames.indexOf(paramName)).equals(HttpServletRequest.class)) params.put(paramName, req);
                if (paramTypes.get(paramNames.indexOf(paramName)).equals(HttpServletResponse.class)) params.put(paramName, resp);
            }
            method.invoke(obj, params.values().toArray(new Object[params.size()]));
            return "200";
        } catch (Exception e) {
            e.printStackTrace();
            return "500";
        }
    }

    private static List<String> getParamNames(String methodName) throws Exception {
        List<String> paramNames = new ArrayList<>();
        Class<?> clazz = TestController.class;
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
