/**
 * 
 */
package com.monstersoftwarellc.timeease.model.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Different options for Billing Methods that Freshbooks supports.
 * @author nick
 *
 */
@XmlEnum
public enum BillingMethodType {
	@XmlEnumValue("task-rate")
	TASK("task-rate"),
	@XmlEnumValue("flat-rate")
	FLAT("flat-rate"),
	@XmlEnumValue("project-rate")
	PROJECT("project-rate"),
	@XmlEnumValue("staff-rate")
	STAFF("staff-rate");

	private String value;
	
	
	/**
	 * @param type
	 */
	private BillingMethodType(String value) {
		this.value = value;
	}

	/**
	 * The string literal for the given method type.
	 * @return the type
	 */
	public String getType() {
		return value;
	}
}
