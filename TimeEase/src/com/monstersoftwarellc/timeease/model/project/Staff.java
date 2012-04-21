/**
 * 
 */
package com.monstersoftwarellc.timeease.model.project;

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
@XmlRootElement(name="staff")
@Entity
public class Staff implements IFreshbooksEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	/**
	 * @return the id
	 */
	@XmlElement(name="staff_id")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.model.IFreshbooksEntity#getFreshbooksObjectName()
	 */
	@Override
	public String getFreshbooksObjectName() {
		return "staff";
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
		return "Staff";
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.model.IFreshbooksEntity#getLabel()
	 */
	@Override
	public String getLabel() {
		return "Staff: " + id;
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
		return new HashCodeBuilder(17, 31)
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
