/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import org.eclipse.jface.viewers.ISelectionChangedListener;

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
	
	public ISelectionChangedListener getSelectionChangedListener();
}
