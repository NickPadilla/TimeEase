/**
 * 
 */
package com.monstersoftwarellc.timeease.enums;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author nicholas
 *
 */
public enum SearchOperators implements IEnum {
	
	GREATER_THAN(">"),
	GREATER_THAN_OR_EQUAL(">="),
	LESS_THAN("<"),
	LESS_THAN_OR_EQUAL("<="),
	EQUAL_TO("="),
	NOT_EQUAL_TO("!=");

	private String label;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	private SearchOperators(String label){
		this.label = label;
	}
	

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.ILabel#getLabel()
	 */
	public String getLabel(){
		return label;
	}
	
	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName,
				listener);
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.goldrush.enums.IEnum#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue,
				newValue);
	}
}
