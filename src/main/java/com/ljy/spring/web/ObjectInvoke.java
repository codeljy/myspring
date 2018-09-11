package com.ljy.spring.web;

import com.ljy.spring.bean.BeanMessage;
import com.ljy.spring.bean.BeanMessage.MethodMessage;
import com.ljy.spring.bean.UrlMethodRelate;
import com.ljy.spring.factory.BeanFactory;
import com.ljy.spring.factory.UrlFactory;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
            // 获取请求uri
            String uri = req.getRequestURI().toString();
            // 处理uri后面拼接的参数
            if(uri.lastIndexOf("?") != -1) uri = uri.substring(0, uri.lastIndexOf("?"));
            // 通过uri获取对应的UrlMethodRelate
            UrlMethodRelate urlMethodRelate = UrlFactory.instance().gainUrlMethodRelate(uri);
            if (urlMethodRelate == null) return "404";
            // 获取对应的BeanMessage
            BeanMessage beanMessage = BeanFactory.instance().gainBean(urlMethodRelate.getClassName());
            // 获取要执行的对象
            Object obj = beanMessage.gainInvokeObj();
            // 获取方法信息对象
            MethodMessage mm = beanMessage.gainMethod(urlMethodRelate.getMethodName());
            // 获取方法
            Method method = mm.getMethod();
            // 获取入参类型列表
            List<Class> paramTypes = mm.getParamTypes();
            // 获取入参名称列表
            List<String> paramNames = mm.getParamNames();
            // 创建入参map
            Map<String, Object> params = new LinkedHashMap<>();
            // 遍历入参名称列表，匹配入参
            for (String paramName : paramNames) {
                params.put(paramName, req.getParameter(paramName));
                if (paramTypes.get(paramNames.indexOf(paramName)).equals(HttpServletRequest.class)) params.put(paramName, req);
                if (paramTypes.get(paramNames.indexOf(paramName)).equals(HttpServletResponse.class)) params.put(paramName, resp);
            }
            // 反射执行对象的方法
            method.invoke(obj, params.values().toArray(new Object[params.size()]));
            return "200";
        } catch (Exception e) {
             e.printStackTrace();
            return "500";
        }
    }



}
