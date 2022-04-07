package com.xzq.service;

import com.xzq.spring.BeanNameAware;
import com.xzq.spring.InitializingBean;
import com.xzq.spring.annotation.Autowired;
import com.xzq.spring.annotation.Component;

@Component("userService")
public class UserService implements BeanNameAware, InitializingBean, UserInterface {

    @Autowired
    private OrderService orderService;

    private String beanName;

    private String beanAttr;

    public void test(){
//        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("正在初始化");
    }
}
