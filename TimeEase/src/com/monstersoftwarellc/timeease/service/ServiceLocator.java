/**
 * 
 */
package com.monstersoftwarellc.timeease.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;


/**
 * @author nick
 *
 */
public class ServiceLocator implements BeanFactoryAware, IServiceLocator {
		
		private static IServiceLocator beanLocator = null;
		private BeanFactory beanFactory=null;
		
		/**
		 * 
		 */
		private ServiceLocator(){
			
		}
		
		/**
		 * @return
		 */
		public static IServiceLocator getCurrentInstance(){
			if(beanLocator == null){
				beanLocator = new ServiceLocator();
			}
			return beanLocator;
		}
		
		
		/**
		 * Returns a "live" instance of the given class from the "current" ServiceLocator. This will either come from the spring container or be created.
		 * @param clazz the class of the object to be created 
		 * @return The instance of the given class. 
		 * @throws {@link IllegalStateException} if there is a problem getting the class from the Spring Application Context.
		 */
		public static <T> T locateCurrent( Class<T> clazz)throws IllegalStateException{
			return getCurrentInstance().locate(clazz);
		}
		

		/* (non-Javadoc)
		 * @see com.goldrush.service.IServiceLocator#locate(java.lang.Class)
		 */
		@SuppressWarnings("unchecked")
		public <T> T locate( Class<T> clazz)throws IllegalStateException{
			try {
		
				Object object = BeanFactoryUtils.beanOfTypeIncludingAncestors((ListableBeanFactory)beanFactory, clazz);
				
				if(object == null){
					throw new IllegalStateException("No Class Found For Name " + clazz.getName() + " in Spring Context");
				}
				
				return (T)object;
			} catch (BeansException e) {
				throw new IllegalStateException(e);
			}
		}
		
		
		/* (non-Javadoc)
		 * @see com.goldrush.service.IServiceLocator#getBean(java.lang.String)
		 */
		public Object getBean(String beanName)throws IllegalStateException{
			try {
				Object service = null;
				
				service = beanFactory.getBean(beanName);
				
				if(service == null){
					throw new IllegalStateException("No bean for name " + beanName + " found in Spring Context");
				}
				
				return service;
			} catch (BeansException e) {
				throw new IllegalStateException(e);
			}
		}

		/* (non-Javadoc)
		 * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
		 */
		@Override
		public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
			this.beanFactory = beanFactory;
		}

}