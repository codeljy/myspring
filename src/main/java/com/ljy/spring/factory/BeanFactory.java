package com.ljy.spring.factory;

import com.ljy.spring.bean.BeanMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:BeanFactory
 * @Description:对象工厂，用于存储初始化的对象及获取对象
 * @Author: ljy
 * @Date: 2018/8/30
 **/
public class BeanFactory {

    private static BeanFactory factory = null;

    public static BeanFactory instance() {
        if (factory == null) factory = new BeanFactory();
        return factory;
    }

    private List<BeanMessage> beans = new ArrayList<>();

    public BeanMessage gainBean(String targe) {
        for (BeanMessage b : beans) {
            BeanMessage beanMessage = b.gainBeanMessage(targe);
            if (beanMessage != null) return beanMessage;
        }
        return null;
    }

    public List<BeanMessage> getBeans() {
        return beans;
    }

    public List<BeanMessage> gainControllers() {
        List<BeanMessage> list = new ArrayList<>();
        for (BeanMessage b : beans) {
            if (b.isController()) list.add(b);
        }
        return list;
    }

    public List<BeanMessage> gainServices() {
        List<BeanMessage> list = new ArrayList<>();
        for (BeanMessage b : beans) {
            if (b.isService()) list.add(b);
        }
        return list;
    }

    public List<BeanMessage> gainAops() {
        List<BeanMessage> list = new ArrayList<>();
        for (BeanMessage b : beans) {
            if (b.isAop()) list.add(b);
        }
        return list;
    }

    public boolean addBean(BeanMessage bm) {
        return beans.add(bm);
    }

}
