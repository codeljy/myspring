package com.ljy.spring.web;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:ClassAnnotationReader
 * @Description:注解读取解析
 * @Author: ljy
 * @Date: 2018/7/27
 **/
public class ClassAnnotationReader {

    public static List<Class> clazzs = new ArrayList<>();

    /**
     * @author: ljy
     * @date: 2018/9/4
     * @description: 遍历当前项目的文件，获取所有class对象
     */
    public static void read() {
        try {
            // 获取根目录
            File file = new File(ClassAnnotationReader.class.getResource("../").toURI());
            // 获取所有的class文件
            fileRead(file);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author: ljy
     * @date: 2018/9/4
     * @description: 遍历所有class文件，获取class对象
     */
    private static void fileRead(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            // 如果是文件夹，则递归执行
            if (f.isDirectory()) {
                fileRead(f);
            } else {
                // 将路径中的\转换为.
                String path = f.getPath().replace("\\", ".");
                // 非class文件则跳过
                if (!path.endsWith(".class")) continue;
                String className = path
                        .substring(path.lastIndexOf("classes.") + 8, path.lastIndexOf(".class"));
                try {
                    // 添加class对象到clazzs中存储
                    clazzs.add(Class.forName(className));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*public static void main(String[] args) throws Exception {
        ClassAnnotationReader.read();
        Map<String, MethodMapper> urlMapping = BeanContainer.urlMapping;
        Map<String, Object> beanMapping = BeanContainer.beanMapping;
        Map<String, String> controllerNameClassMapping = BeanContainer.controllerNameClassMapping;
        Map<String, String> serviceNameClassMapping = BeanContainer.serviceNameClassMapping;
        System.out.println("hehe");
    }*/

}
