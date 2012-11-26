/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import com.monstersoftwarellc.timeease.property.annotations.PropertyChoice;


/**
 * @author navid
 *
 */
public interface IPropertyMetadata {
	
	public String getLabel();
	
	public String getName();
	
	public Class<?> getType();
	
	public Class<?> getListType();
	
	public boolean isShared();
	
	public boolean hasChoice();
	
	public boolean isHidden();
	
	public Integer getSequence();
	
	public String[] getUiCustomizations();
	
	/**
	 * Methods below belong to the property parent framework.  This allows properties to 
	 * have dependents that can be notified on a selection changed event and have its input
	 * recalculated. This framework will only work when PropertyChoice value is used.
	 */
	
	public IPropertyMetadata getDependentProperty();
	
	public void setDependentProperty(IPropertyMetadata dependentProperty);
	
	public boolean isDependent();
	
	
	/**
	 * Returns the object that holds the ui selections, from the {@link PropertyChoice} SPEL return value. 
	 * @return
	 */
	public Object getViewerObject();
	public void setViewerObject(Object viewerObject);
}
