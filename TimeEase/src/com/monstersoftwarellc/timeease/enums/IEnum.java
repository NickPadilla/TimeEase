/**
 * 
 */
package com.monstersoftwarellc.timeease.enums;

import java.beans.PropertyChangeListener;


/**
 * @author nicholas
 *
 */
public interface IEnum extends ILabel {

	/**
	 * All accessible objects need to have these {@link PropertyChangeListener} supporting methods. These are object that will be
	 * handled by JFace Databinding.
	 * @param listener
	 */
	public abstract void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * All accessible objects need to have these {@link PropertyChangeListener} supporting methods. These are object that will be
	 * handled by JFace Databinding.
	 * @param propertyName
	 * @param listener
	 */
	public abstract void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

	/**
	 * All accessible objects need to have these {@link PropertyChangeListener} supporting methods. These are object that will be
	 * handled by JFace Databinding.
	 * @param listener
	 */
	public abstract void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * All accessible objects need to have these {@link PropertyChangeListener} supporting methods. These are object that will be
	 * handled by JFace Databinding.
	 * @param propertyName
	 * @param listener
	 */
	public abstract void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

	/**
	 * All accessible objects need to have these {@link PropertyChangeListener} supporting methods. These are object that will be
	 * handled by JFace Databinding.
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 */
	abstract void firePropertyChange(String propertyName, Object oldValue,
			Object newValue);
}
