package com.xzq.spring;

import com.xzq.spring.annotation.Autowired;
import com.xzq.spring.annotation.Component;
import com.xzq.spring.annotation.ComponentScan;
import com.xzq.spring.annotation.Scope;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * spring 容器类
 */
public class XzqApplicationContext {
    private Class configClass;

    //用来存储扫描到的bean
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    //单例池
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public XzqApplicationContext(Class configClass) {
        this.configClass = configClass;

        //扫描
        //判断是否有扫描的注解
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

                                //获取bean的名字
                                Component component = clazz.getAnnotation(Component.class);
                                String beanName = component.value();
                                if(beanName.equals("")){
                                    // introspector：内省
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());//beanName 首字母会小写，详情见该方法内容
                                }

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

                                beanDefinitionMap.put(beanName, beanDefinition);

                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }

        //查询 实例化单例bean
        for (String beanName : beanDefinitionMap.keySet()) {

            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

            if (beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }

    /**
     * 创建bean
     * @param BeanName
     * @param beanDefinition
     * @return
     */
    private Object createBean(String BeanName, BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getType();

        try {
            Object instance = clazz.getConstructor().newInstance();

            //实现依赖注入------>简单版
            for (Field f : clazz.getDeclaredFields()) {
                System.out.println(f.getType());
                //判断是否有 Autowired 注解
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);                  //使用反射需要修改为 true
                    f.set(instance, getBean(f.getName()));  //这里需要注入的变量名，与类型名一致，忽略首字母大小写；列: UserService : userService
                }
            }

            //aware


            return instance;

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取bean
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null){
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();

            if (scope.equals("singleton")){
                //如果是单例的，直接从单例池获取
                Object bean = singletonObjects.get(beanName);
                if (bean == null){
                    Object o = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, o);
                } else {
                    return bean;
                }
            } else {
                //多例bean
                return createBean(beanName, beanDefinition);
            }
        }
        return null;
    }
}
