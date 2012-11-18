/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import java.lang.reflect.Proxy;

import org.springframework.stereotype.Service;

import com.monstersoftwarellc.timeease.model.IPropertyChangeSupport;


/**
 * Provides dynamic property access for any interface wishing to act as a property class
 * All interface methods must follow standard pojo naming conventions. set methods set the value for the property. get and is methods return the value. The rest of the method name is used to determine the propety name. 
 * @author navid
 *
 */
@Service
public class PropertyService implements IPropertyService {

	/* (non-Javadoc)
	 * @see com.goldrush.service.impl.IPropertyService#getProperties(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getProperties(Class<T> clazz){
		if(!clazz.isInterface()){
			throw new IllegalArgumentException("Clazz must be an Interface.");
		}

		return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
				new Class[] { clazz, IPropertyChangeSupport.class, IPropertyProxy.class },
				new PropertyProxyInvocationHandler(clazz));
	}
	
	


}
