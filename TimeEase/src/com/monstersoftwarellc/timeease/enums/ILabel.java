/**
 * 
 */
package com.monstersoftwarellc.timeease.enums;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * @author nicholas
 *
 */
public interface ILabel {
	
	/**
	 * Enables the use of a custom {@link LabelProvider} to get at the objects user friendly description.
	 * @return
	 */
	public abstract String getLabel();

}
