package com.xzq.spring;

import java.lang.annotation.Annotation;

/**
 * spring 容器类
 */
public class XzqApplicationContext {
    private Class configClass;

    public XzqApplicationContext(Class configClass) {
        this.configClass = configClass;

        //扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);

            String path = componentScanAnnotation.value();  //扫描路径  com.xzq.service
            path = path.replace(".", "/");//com/xzq/service


            //D:\xzq\workspace\spring-demo\out\production\spring-demo
            ClassLoader classLoader = componentScanAnnotation.getClass().getClassLoader();  //获取类加载器
            classLoader.getResource(path);
        }
    }

    public Object getBean(String beanName){

        return null;
    }
}
