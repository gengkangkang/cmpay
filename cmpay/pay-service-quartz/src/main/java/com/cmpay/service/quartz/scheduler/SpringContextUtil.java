package com.cmpay.service.quartz.scheduler;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 *  spring工具类
 *
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年12月27日 下午1:44:56
 *
 */
public class SpringContextUtil implements ApplicationContextAware {

	// Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Override
	public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取对象 这里重写了bean方法，起主要作用
     */
    public static Object getBean(String beanId) throws BeansException {
        return applicationContext.getBean(beanId);
    }

    public static String getPropertieByName(String name){
    	ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        Properties result = parser.parseExpression("@env").getValue(context, Properties.class);

    	return result.getProperty(name);
    }

}
