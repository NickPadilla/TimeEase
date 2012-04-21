/**
 * 
 */
package com.monstersoftwarellc.timeease.model.client;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.impl.Account;

/**
 * @author nicholas
 *
 */
@XmlRootElement(name="contact")
@Entity
public class Contact implements IFreshbooksEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long externalId;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phoneOne;
	
	private String phoneTwo;

	/**
	 * @return the id
	 */
	@XmlElement(name="contact_id")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the externalId
	 */
	public Long getExternalId() {
		return externalId;
	}

	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	/**
	 * @return the username
	 */
	@XmlElement(name="username")
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the firstName
	 */
	@XmlElement(name="first_name")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@XmlElement(name="last_name")
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	@XmlElement(name="email")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneOne
	 */
	@XmlElement(name="phone1")
	public String getPhoneOne() {
		return phoneOne;
	}

	/**
	 * @param phoneOne the phoneOne to set
	 */
	public void setPhoneOne(String phoneOne) {
		this.phoneOne = phoneOne;
	}

	/**
	 * @return the phoneTwo
	 */
	@XmlElement(name="phone2")
	public String getPhoneTwo() {
		return phoneTwo;
	}

	/**
	 * @param phoneTwo the phoneTwo to set
	 */
	public void setPhoneTwo(String phoneTwo) {
		this.phoneTwo = phoneTwo;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.model.IFreshbooksEntity#getFreshbooksObjectName()
	 */
	@Override
	public String getFreshbooksObjectName() {
		return "contact";
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.model.IFreshbooksEntity#getFreshbooksObjectId()
	 */
	@Override
	public Integer getFreshbooksObjectId() {
		return id.intValue();
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.model.IFreshbooksEntity#getFreshbooksObjectLabel()
	 */
	@Override
	public String getFreshbooksObjectLabel() {
		return "Contact";
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.model.IFreshbooksEntity#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Contact: " + lastName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// performance optimization
		if(obj == this){
			return true;
		}
		if(!(obj instanceof Account)){
			return false;
		}
		Account account = (Account) obj;
		
		return new EqualsBuilder()
								.append(account.getId(), getId())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(13, 31)
								.append(getId())
								.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, true);
	}
}
