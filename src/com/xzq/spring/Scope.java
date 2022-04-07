package com.xzq.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //注解生效的时间
@Target(ElementType.TYPE)           //指定写类上面
public @interface Scope {

    //判断 Bean 是单例， 还是多例
    String value() default "";
}
