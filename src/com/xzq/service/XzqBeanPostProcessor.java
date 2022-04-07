package com.xzq.service;

import com.xzq.spring.BeanPostProcessor;
import com.xzq.spring.annotation.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class XzqBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessorBeforeInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")){
            System.out.println("hello world");
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(String beanName, Object bean) {
        if (beanName.equals("userService")){
            //创建代理对象
            Object proxyInstance = Proxy.newProxyInstance(XzqBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args);
                }
            });

            return proxyInstance;
        }
        return bean;
    }

}
