package cn.com.bocosoft.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * Spring 的注入bean工具类。
 * 可根据bean名称取得相应的bean。
 * @author Houbz
 */
@Component
public class SpringBeanUtils implements BeanFactoryAware {
    private static BeanFactory beanFactory = null;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public  BeanFactory getBeanFactory() {
        return beanFactory;
    }
    
   public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }

    public static Object getBean(String beanName, Class<?> clazz) {
        return beanFactory.getBean(beanName, clazz);
    }

}