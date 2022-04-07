package com.xzq.spring;

/**
 * 初始化是对bean进行的操作
 */
public interface BeanPostProcessor {

    //初始化前
    public Object postProcessorBeforeInitialization(String beanName, Object bean);
    //初始化后
    public Object postProcessorAfterInitialization(String beanName, Object bean);

}
