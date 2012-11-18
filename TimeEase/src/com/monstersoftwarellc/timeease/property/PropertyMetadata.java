/**
 * 
 */
package com.monstersoftwarellc.timeease.property;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.ISelectionChangedListener;

/**
 * @author navid
 *
 */
class PropertyMetadata implements IPropertyMetadata {

	protected String label;
	protected String name;
	protected String longName;
	protected String choiceExpression = null;
	protected String defaultExpression = null;
	protected Class<?> type;
	protected Class<?> listType;
	protected Boolean shared = null;
	protected boolean hasGetter;
	protected boolean hasSetter;
	protected Boolean hidden = null;
	protected Integer sequence;
	protected String[] uiCustomizations;

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadata#getLabel()
	 */
	@Override
	public String getLabel() {
		// if the label is not set we use the name
		if(label == null){
			label = splitCamelCase(StringUtils.capitalize(name));
		}
		return label;
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadata#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadata#getType()
	 */
	@Override
	public Class<?> getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadata#hasChoice()
	 */
	@Override
	public boolean hasChoice() {
		return choiceExpression != null;
	}

	/* (non-Javadoc)
	 * @see com.goldrush.property.IPropertyMetadata#isShared()
	 */
	@Override
	public boolean isShared() {
		if(shared ==null){
			shared = true;
		}
		return shared;
	}
	
	public String getLongName() {
		return longName;
	}

	/**
	 * @return the choiceExpression
	 */
	public String getChoiceExpression() {
		return choiceExpression;
	}

	/**
	 * @param choiceExpression the choiceExpression to set
	 */
	public void setChoiceExpression(String choiceExpression) {
		this.choiceExpression = choiceExpression;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * @param shared the shared to set
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
	}


	private String splitCamelCase(String s) {
		   return s.replaceAll(
		      String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      ),
		      " "
		   );
		}

	/**
	 * @return the hasGetter
	 */
	public boolean isHasGetter() {
		return hasGetter;
	}

	/**
	 * @param hasGetter the hasGetter to set
	 */
	public void setHasGetter(boolean hasGetter) {
		this.hasGetter = hasGetter;
	}

	/**
	 * @return the hasSetter
	 */
	public boolean isHasSetter() {
		return hasSetter;
	}

	/**
	 * @param hasSetter the hasSetter to set
	 */
	public void setHasSetter(boolean hasSetter) {
		this.hasSetter = hasSetter;
	}

	/**
	 * @return the defaultExpression
	 */
	public String getDefaultExpression() {
		return defaultExpression;
	}

	/**
	 * @param defaultExpression the defaultExpression to set
	 */
	public void setDefaultExpression(String defaultExpression) {
		this.defaultExpression = defaultExpression;
	}

	/**
	 * @return the hidden
	 */
	public boolean isHidden() {
		if(hidden == null){
			hidden = false;
		}
		return hidden;
	}

	/**
	 * @param hidden the hidden to set
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the uiCustomizations
	 */
	public String[] getUiCustomizations() {
		return uiCustomizations;
	}

	/**
	 * @param uiCustomizations the uiCustomizations to set
	 */
	public void setUiCustomizations(String[] uiCustomizations) {
		this.uiCustomizations = uiCustomizations;
	}
	
	public Class<?> getListType() {
		return listType;
	}
	
	public void setListType(Class<?> listType) {
		this.listType = listType;
	}

	@Override
	public ISelectionChangedListener getSelectionChangedListener() {
		// TODO Auto-generated method stub
		return null;
	}
}
