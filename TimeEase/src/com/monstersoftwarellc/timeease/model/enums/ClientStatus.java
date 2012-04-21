/**
 * 
 */
package com.monstersoftwarellc.timeease.model.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Enum class will specify the client status.  <br/>
 * We can query over this, this is why it is handy to have <br/>
 * this class around.
 * @author nick
 *
 */
@XmlEnum
public enum ClientStatus {
	@XmlEnumValue("active")
	ACTIVE("active"),
	@XmlEnumValue("archived")
	ARCHIVED("archived"),
	@XmlEnumValue("deleted")
	DELETED("deleted");
	
	private String value;
	
	private ClientStatus(String value){
		this.value = value;
	}

	/**
	 * Gets the actual string value.
	 * @return
	 */
	public String getValue(){
		return value;
	}

}
