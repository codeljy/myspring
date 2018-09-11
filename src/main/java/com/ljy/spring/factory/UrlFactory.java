package com.ljy.spring.factory;

import com.ljy.spring.bean.UrlMethodRelate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:BeanFactory
 * @Description:对象工厂，用于存储初始化的对象及获取对象
 * @Author: ljy
 * @Date: 2018/8/30
 **/
public class UrlFactory {

    private static UrlFactory factory = null;

    public static UrlFactory instance() {
        if (factory == null) factory = new UrlFactory();
        return factory;
    }

    private List<UrlMethodRelate> urlMethodRelates = new ArrayList<>();

    public UrlMethodRelate gainUrlMethodRelate(String url) {
        for (UrlMethodRelate u : urlMethodRelates) {
            if (u.getUrl().equals(url)) return u;
        }
        return null;
    }

    public boolean addUrlMethodRelate(UrlMethodRelate umr) {
        return urlMethodRelates.add(umr);
    }

}