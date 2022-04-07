package com.xzq.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义扫描路径
 */
@Retention(RetentionPolicy.RUNTIME) //注解生效的时间
@Target(ElementType.TYPE)           //指定写类上面
public @interface ComponentScan {

    //指定扫描路径
    String value() default "";
}
