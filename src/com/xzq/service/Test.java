package com.xzq.service;

import com.xzq.spring.XzqApplicationContext;

public class Test {
    public static void main(String[] args) {

        //需要传入一个配置文件,或者一个配置类
        XzqApplicationContext xzqApplicationContext = new XzqApplicationContext(AppConfig.class);

        UserService userService = (UserService) xzqApplicationContext.getBean("userService");
        userService.test();
//        System.out.println(xzqApplicationContext.getBean("userService"));
        System.out.println("this is spring demo");
    }
}
