package org.devgateway.toolkit.persistence.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T extends Object> T getBean(final Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }
}
