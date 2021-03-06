package com.ljy.spring.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName:DispatcherServlet
 * @Description:
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class DispatcherServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setHeader("Content-type", "text/html;charset=UTF-8");
            resp.setCharacterEncoding("utf-8");
            // 反射执行
            String responseCode = ObjectInvoke.invoke(req, resp);
            // 404处理
            if ("404".equals(responseCode)) {
                PrintWriter writer = resp.getWriter();
                writer.write("404");
                writer.close();
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // 类注解扫描
        ClassAnnotationReader.read();
        // 对象实例化
        ObjectInstantiates.instan(ClassAnnotationReader.clazzs);
        // aop代理对象生成
        AopProxyInstantiates.instan();
        // url与方法关联
        UrlMethodMapper.readUrl();
        // 对象注入
        ParamInsert.insert();
    }

}
