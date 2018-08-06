package com.ljy.spring.web;

import com.ljy.spring.bean.BeanContainer;
import com.ljy.spring.bean.MethodMapper;
import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:ClassAnnotationReader
 * @Description:注解读取解析
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class ClassAnnotationReader {

    public static List<Class> clazzs = new ArrayList<>();

    public static void read() {
        try {
            File file = new File(ClassAnnotationReader.class.getResource("../").toURI());
            System.out.println(file.getPath());
            fileRead(file);
            try {
                UrlMethodMapper.readUrl();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void fileRead(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                fileRead(f);
            } else {
                String path = f.getPath().replace("\\", ".");
                if (!path.endsWith(".class")) continue;
                String className = path
                        .substring(path.lastIndexOf("classes.") + 8, path.lastIndexOf(".class"));
                try {
                    clazzs.add(Class.forName(className));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ClassAnnotationReader.read();
        Map<String, MethodMapper> urlMapping = BeanContainer.urlMapping;
        Map<String, Object> beanMapping = BeanContainer.beanMapping;
        Map<String, String> controllerNameClassMapping = BeanContainer.controllerNameClassMapping;
        Map<String, String> serviceNameClassMapping = BeanContainer.serviceNameClassMapping;
        System.out.println("hehe");
    }

}
