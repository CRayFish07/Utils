package com.netease.welkin.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring bean util.
 * 
 * @author zxwu
 */
public final class SpringBeanUtil {
    /**
     * Constructor
     */
    private SpringBeanUtil() {
    }

    private static ApplicationContext context;

    private static ApplicationContext getApplicationContext() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
        }
        return context;
    }

    /**
     * 根据提供的bean名称得到相应的服务类
     * 
     * @param name bean名称
     * @return bean.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 根据提供的bean class得到相应的服务类
     * 
     * @param <T> class type.
     * @param clazz class.
     * @return bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

}
