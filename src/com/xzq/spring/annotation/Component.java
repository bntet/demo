package com.xzq.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义bean
 */
@Retention(RetentionPolicy.RUNTIME) //注解生效的时间
@Target(ElementType.TYPE)           //指定写类上面
public @interface Component {

    //给当前的bean 起名字
    String value() default "";
}
