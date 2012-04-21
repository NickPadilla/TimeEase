/**
 * 
 */
package com.monstersoftwarellc.timeease.model.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Enum for centralizing the Freshbooks Response Status, if is passed or failed.
 * @author nick
 *
 */
@XmlEnum
public enum ResponseStatus {
	
	@XmlEnumValue("ok")
	SUCCESSFUL("ok"),
	@XmlEnumValue("fail")
	FAILED("fail");
	
	private String value;

	/**
	 * @param value
	 */
	private ResponseStatus(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
