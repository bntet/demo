package com.xzq.service;

import com.xzq.spring.BeanNameAware;
import com.xzq.spring.annotation.Autowired;
import com.xzq.spring.annotation.Component;

@Component("userService")
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    public void test(){
        System.out.println(orderService);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
