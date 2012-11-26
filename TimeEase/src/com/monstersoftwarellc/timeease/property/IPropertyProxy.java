/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import java.util.List;


/**
 * All property proxies returned by {@link IPropertyService#getProperties(Class)} will automatically implement this interface.
 * @author navid
 *
 */
public interface IPropertyProxy {

	public List<IPropertyMetadata> getPropertyMetas();
	
	/**
	 * Returns the property meta for the property name.
	 * @param name the name of the property.
	 * @return the {@link IPropertyMetadata}
	 */
	public IPropertyMetadata getPropertyMeta(String name);

	
	public Object getChoice(IPropertyMetadata meta);
	
	public Object getPropertyValue(IPropertyMetadata meta);
	
	public void setPropertyValue(IPropertyMetadata meta,Object value);
	
	public Object getPropertyValueByMetaName(String propertyMetaName);
	
}
