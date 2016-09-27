package com.netease.welkin.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author zxwu
 */
@Component
public final class ServiceFactory {

    private ServiceFactory() {
    }

    private static ServiceFactory serviceFactory;
    private static ApplicationContext context;

    /**
     * @return ServiceFactory
     */
    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            context = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
            serviceFactory = (ServiceFactory) context.getBean("serviceFactory");
        }
        return serviceFactory;
    }

    /**
     * 根据提供的bean名称得到相应的服务类
     * 
     * @param name bean名称
     * @return Object
     */
    public Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 根据提供的bean class得到相应的服务类
     * 
     * @param <T> <T>
     * @param clazz clazz
     * @return <T>
     */
    public <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

}
