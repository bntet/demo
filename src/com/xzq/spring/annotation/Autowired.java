package com.xzq.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 依赖注入
 */
@Retention(RetentionPolicy.RUNTIME) //注解生效的时间
@Target(ElementType.FIELD)          //指定写 字段 上面
public @interface Autowired {
}
