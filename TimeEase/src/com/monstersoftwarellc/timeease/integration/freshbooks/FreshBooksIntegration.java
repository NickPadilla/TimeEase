/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.monstersoftwarellc.timeease.integration.AbstractIntegration;
import com.monstersoftwarellc.timeease.model.impl.Account;

/**
 * @author nicholas
 *
 */
@Entity
public class FreshBooksIntegration extends AbstractIntegration {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1773154040928139741L;

	private String url;
	
	private String tokenOrPassword;
	
	private String userName;

	/**
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return
	 */
	public String getTokenOrPassword() {
		return tokenOrPassword;
	}

	/**
	 * @param tokenOrPassword
	 */
	public void setTokenOrPassword(String freshbooksToken) {
		this.tokenOrPassword = freshbooksToken;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setFreshbooksUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
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
		return new HashCodeBuilder(15, 31)
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
