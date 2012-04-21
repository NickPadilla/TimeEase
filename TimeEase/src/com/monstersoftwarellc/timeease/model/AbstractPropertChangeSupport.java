/**
 * 
 */
package com.monstersoftwarellc.timeease.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.monstersoftwarellc.timeease.enums.ILabel;


/**
 * Offers support to objects that don't need a description or tooltip.
 * @author nicholas
 *
 */
public abstract class AbstractPropertChangeSupport implements ILabel {
	
	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}
}
