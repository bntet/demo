package com.xzq.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring 容器类
 */
public class XzqApplicationContext {
    private Class configClass;

    //用来存储扫描到的bean
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public XzqApplicationContext(Class configClass) {
        this.configClass = configClass;

        //扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);

            String path = componentScanAnnotation.value();      //扫描路径  com.xzq.service
            path = path.replace(".", "/");   //com/xzq/service

            ClassLoader classLoader = componentScanAnnotation.getClass().getClassLoader();  //获取类加载器
            //D:/xzq/workspace/spring-demo/out/production/spring-demo  +  /com/xzq/service
            URL resource = classLoader.getResource(path);


            File file = new File(resource.getFile());
            path = path.replace("/", "\\");
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {

                    //筛选 .class
                    String fileName = f.getAbsolutePath();
                    if (fileName.endsWith(".class")) {

                        String className = fileName.substring(fileName.indexOf(path), fileName.indexOf(".class"));
                        className = className.replace("\\", ".");
                        try {
                            Class<?> clazz = classLoader.loadClass(className);//  需要全限定名----》 "com.xzq.service.userService"

                            //判断是否有bean注解
                            if (clazz.isAnnotationPresent(Component.class)) {
                                //Bean
                                //还要要判断 Bean 是单例，还是多例
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(clazz);
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }


                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }

    public Object getBean(String beanName){

        return null;
    }
}
