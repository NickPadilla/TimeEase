/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import java.beans.PropertyChangeListener;

/**
 * @author navid
 *
 */
public interface IPropertyChangeSupport {

	public abstract void addPropertyChangeListener(
			PropertyChangeListener listener);

	public abstract void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

	public abstract void removePropertyChangeListener(
			PropertyChangeListener listener);

	public abstract void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener);

}