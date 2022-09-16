package com.ssfw.common.centext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 类说明：与Spring相关的工具类,该类的所有成员方法均为静态方法,用以获取Spring容器中的
 * 上下文信息
 *
 * @author a
 * @date 2016-11-21下午03:50:50
 *
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext)
			throws BeansException {
		//在加载Spring时主动获得context
		if (null == SpringContextHolder.applicationContext){
			SpringContextHolder.applicationContext = applicationContext;
		}
	}

	/**
	 * 获取bean
	 * @param name Bean Name
	 * @return
	 */
	public static Object getBean(String name){
		if(null==applicationContext) {
			return null;
		}
		return applicationContext.getBean(name);
	}

	public static void setContext(@NotNull ApplicationContext applicationContext){
		SpringContextHolder.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取bean
	 * @param clazz Bean Class
	 * @return
	 */
	public static <T> T getBean(@NotNull Class<T> clazz){
		if(null==applicationContext) {
			return null;
		}
		return applicationContext.getBean(clazz);
	}
	/**
	 * 获取bean
	 * @param clazz Bean Class
	 * @return
	 */
	public static <T> Optional<T> optionalBean(@NotNull Class<T> clazz){

		if(null==applicationContext) {
			return Optional.empty();
		}
		try {
			return Optional.of(applicationContext.getBean(clazz));
		}catch (NoSuchBeanDefinitionException ignore){}

		return Optional.empty();
	}

	/**
	 * 获取bean
	 * @param clazz Bean Class
	 * @return
	 */
	public static <T> Optional<T> optionalBean(@NotNull Class<T> clazz, Consumer<? super T> action){

		Optional<T> optional = optionalBean(clazz);
		optional.ifPresent(action);
		return optional;
	}

	/**
	 * 动态注入bean
	 * @param beanName bean名称
	 * @param clazz bean类
	 * @param propertyValues 属性
	 * @return 注入后的bean
	 */
	public static Object registerBean(@NotNull String beanName,@NotNull Class<?> clazz, @NotNull Map<String,Object> propertyValues){

		return registerBean(beanName,clazz,propertyValues,false,false,null,null);
	}

	/**
	 * 动态注入bean
	 * @param beanName bean名称
	 * @param clazz bean类
	 * @param propertyValues 属性
	 * @param primary 是否主bean
	 * @param lazyInit 是否延迟初始化
	 * @param dependsOn 依赖
	 * @param scope 默认 BeanDefinition.SCOPE_SINGLETON
	 * @return 注入后的bean
	 */
	public static Object registerBean(@NotNull String beanName,Class<?> clazz,
									  @NotNull Map<String,Object> propertyValues,
									  @NotNull boolean primary,
									  @NotNull boolean lazyInit,
									  String[] dependsOn,
									  String scope){
		//获取BeanFactory
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)
				applicationContext.getAutowireCapableBeanFactory();
		//创建bean信息.
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
		if (null != propertyValues){
			for (String key : propertyValues.keySet()) {
				beanDefinitionBuilder.addPropertyValue(key,propertyValues.get(key));
			}
		}
		AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
		beanDefinition.setPrimary(primary);
		if (null!=dependsOn){
			beanDefinition.setDependsOn(dependsOn);
		}
		beanDefinition.setLazyInit(lazyInit);
		if (null != scope){
			beanDefinition.setScope(scope);
		}
		//动态注册bean.
		defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
		return applicationContext.getBean(beanName);
	}

	/**
	 * 动态注入bean
	 * @param beanName bean 名称
	 * @param singleton bean 对象
	 */
	public static Object registerSingleton(@NotNull String beanName,@NotNull Object singleton) {

		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)
				applicationContext.getAutowireCapableBeanFactory();
		defaultListableBeanFactory.registerSingleton(beanName,singleton);
		return applicationContext.getBean(beanName);
	}

	/**
	 * 判断是否存在bean
	 * @param beanName bean名称
	 */
	public static boolean containsBean(String beanName) {
		//applicationContext里面的的方法containsBean == containsLocalBean == containsBeanDefinition，
		// 它们在StaticListableBeanFactory中的实现方法是同一个
		return applicationContext.containsBean(beanName);
	}

	/**
	 * 通过bean类型，判断是否存在bean实列
	 * @param beanClass bean类型
	 */
	public static boolean containsBean(Class<?> beanClass) {
		return getBeanNamesForType(beanClass).length>0;
	}

	/**
	 * 通过bean类型获取bean的实列名称
	 * @param beanClass bean类型
	 * @return bean的实列名称
	 */
	public static String[] getBeanNamesForType(Class<?> beanClass) {
		return applicationContext.getBeanNamesForType(beanClass);
	}

	/**
	 * 获取环境遍历中的配置属性
	 * @param key 配置key
	 * @param defaultValue 默认值
	 * @return 配置值
	 */
	public static String getEnvironmentProperty(String key, String defaultValue){
		return getApplicationContext().getEnvironment().getProperty(key, defaultValue);
	}

	private static final String DEFAULT_APPLICATION_NAME = "application";
	private static final String KEY_APPLICATION_NAME = "spring.application.name";

	/**
	 * 获取应用名称
	 * 在 配置文件中配置：spring.application.name=app
	 * @return 应用名称
	 */
	public static String getApplicationName(){
		return applicationContext.getEnvironment().getProperty(KEY_APPLICATION_NAME, DEFAULT_APPLICATION_NAME);
	}
}
