/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

/**
 * @author nick
 *
 */
public interface IServiceLocator {
	
	/**
	 * Returns a "live" instance of the given class. This will either come from the spring container or be created.
	 * @param clazz the class of the object to be created 
	 * @return The instance of the given class. 
	 * @throws {@link IllegalStateException} if there is a problem getting the class from the Spring Application Context.
	 */
	public abstract <T> T locate(Class<T> clazz) throws IllegalStateException;
	
	/**
	 * Returns a bean name must be registered with the Spring Context
	 * @param beanName name of Bean
	 * @return bean object.
	 * @throws {@link IllegalStateException} if there is a problem getting the bean for the name from the Spring Application Context.
	 */
	public abstract Object getBean(String beanName) throws IllegalStateException;

}
