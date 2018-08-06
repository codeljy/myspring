package com.ljy.spring.controller;


import com.ljy.spring.annotation.LJYContoller;
import com.ljy.spring.annotation.LJYRequestMapping;
import com.ljy.spring.annotation.LJYResource;
import com.ljy.spring.service.TestService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName:TestController
 * @Description:测试入口
 * @Author: ljy
 * @Date: 2018/7/27
 **/
@LJYContoller("test")
@LJYRequestMapping("/test")
public class TestController {

    @LJYResource("testService")
    private TestService testService;

    @LJYRequestMapping("/helloWorld")
    public void helloWorld(HttpServletRequest req, HttpServletResponse resp, String name)
            throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\">\n"
                + "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">\n"
                + "\t<title>myspring</title>\n"
                + "</head>\n"
                + "<body class=\"layui-layout-body\">\n"
                + "Hello "+name+"!\n"+"<br/>"+testService.getPeople(name)
                + "</body>\n"
                + "</html>");
        writer.flush();
        writer.close();
    }

}
