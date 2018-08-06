package com.ljy.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/8/3.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LJYAop {

    public static final String PRO = "pro";

    public static final String AFTER = "after";

    public static final String AROUND = "around";

    String aopBeanName() default "";

    String aopType() default "";

}
