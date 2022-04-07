package com.xzq.spring;

/**
 * bean 的定义
 */
public class BeanDefinition {
    /** Bean的类型*/
    private Class type;
    /** Bean是单例，还是多例的*/
    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
