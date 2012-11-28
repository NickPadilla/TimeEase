/**
 * 
 */
package com.monstersoftwarellc.timeease.model.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.BaseDomain;
import com.monstersoftwarellc.timeease.model.enums.ClientStatus;

/**
 * This class outlines the Client object specification, implements {@link IFreshbooksEntity}.
 * @author nick
 *
 */
@XmlRootElement(name="client")
@Entity
public class Client extends BaseDomain implements IFreshbooksEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2099980834607759592L;	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@Index
	private Account createdBy;

	@ManyToOne(fetch=FetchType.LAZY)
	@Index
	private Account lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	private Integer externalId;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String organization;
	
	private String email;
	
	private String username;
	
	/**
	 * This must be encrypted when stored.
	 */
	private String password;
	
	private String homePhone;
	
	private String workPhone;
	
	private String cellPhone;
	
	private String fax;
	
	private String language;
	
	private String currencyCode;
	
	private String notes;
	
	private String primaryAddress;
	
	private String primaryAddress2;

	private String primaryCity;

	private String primaryState;

	private String primaryCountry;

	private String primaryZipCode;

	private String secondaryAddress;
	
	private String secondaryAddress2;

	private String secondaryCity;

	private String secondaryState;

	private String secondaryCountry;

	private String secondaryZipCode;
	
	private String vatName;
	
	private Integer vatNumber;
	
	private Double credit;
	
	private List<Double> credits;
	
	private String url;
	
	private String clientUrl;
	
	private List<String> links;
	
	private String lastModified;
	
	@Enumerated(EnumType.STRING)
	private ClientStatus clientStatus;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private Contact contact;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Contact> contacts;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Account getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Account createdBy) {
		this.createdBy = createdBy;
	}

	public Account getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(final Account lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	/**
	 * @param externalId the externalId to set
	 */
	public void setExternalId(Integer clientId) {
		this.externalId = clientId;
	}

	/**
	 * @return the externalId
	 */
	@XmlElement(name="client_id")
	public Integer getExternalId() {
		return externalId;
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
	 * @return the organization
	 */
	@XmlElement(name="organization")
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
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
	 * @return the password
	 */
	@XmlElement(name="password")
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the homePhone
	 */
	@XmlElement(name="work_phone")
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @param homePhone the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * @return the workPhone
	 */
	@XmlElement(name="home_phone")
	public String getWorkPhone() {
		return workPhone;
	}

	/**
	 * @param workPhone the workPhone to set
	 */
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	/**
	 * @return the cellPhone
	 */
	@XmlElement(name="mobile")
	public String getCellPhone() {
		return cellPhone;
	}

	/**
	 * @param cellPhone the cellPhone to set
	 */
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	/**
	 * @return the fax
	 */
	@XmlElement(name="fax")
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the language
	 */
	@XmlElement(name="language")
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the currencyCode
	 */
	@XmlElement(name="currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the notes
	 */
	@XmlElement(name="notes")
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the primaryAddress
	 */
	@XmlElement(name="p_street1")
	public String getPrimaryAddress() {
		return primaryAddress;
	}

	/**
	 * @param primaryAddress the primaryAddress to set
	 */
	public void setPrimaryAddress(String primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	/**
	 * @return the primaryAddress2
	 */
	@XmlElement(name="p_street2")
	public String getPrimaryAddress2() {
		return primaryAddress2;
	}

	/**
	 * @param primaryAddress2 the primaryAddress2 to set
	 */
	public void setPrimaryAddress2(String primaryAddress2) {
		this.primaryAddress2 = primaryAddress2;
	}

	/**
	 * @return the primaryCity
	 */
	@XmlElement(name="p_city")
	public String getPrimaryCity() {
		return primaryCity;
	}

	/**
	 * @param primaryCity the primaryCity to set
	 */
	public void setPrimaryCity(String primaryCity) {
		this.primaryCity = primaryCity;
	}

	/**
	 * @return the primaryState
	 */
	@XmlElement(name="p_state")
	public String getPrimaryState() {
		return primaryState;
	}

	/**
	 * @param primaryState the primaryState to set
	 */
	public void setPrimaryState(String primaryState) {
		this.primaryState = primaryState;
	}

	/**
	 * @return the primaryCountry
	 */
	@XmlElement(name="p_country")
	public String getPrimaryCountry() {
		return primaryCountry;
	}

	/**
	 * @param primaryCountry the primaryCountry to set
	 */
	public void setPrimaryCountry(String primaryCountry) {
		this.primaryCountry = primaryCountry;
	}

	/**
	 * @return the primaryZipCode
	 */
	@XmlElement(name="p_code")
	public String getPrimaryZipCode() {
		return primaryZipCode;
	}

	/**
	 * @param primaryZipCode the primaryZipCode to set
	 */
	public void setPrimaryZipCode(String primaryZipCode) {
		this.primaryZipCode = primaryZipCode;
	}

	/**
	 * @return the secondaryAddress
	 */
	@XmlElement(name="s_street1")
	public String getSecondaryAddress() {
		return secondaryAddress;
	}

	/**
	 * @param secondaryAddress the secondaryAddress to set
	 */
	public void setSecondaryAddress(String secondaryAddress) {
		this.secondaryAddress = secondaryAddress;
	}

	/**
	 * @return the secondaryAddress2
	 */
	@XmlElement(name="s_street2")
	public String getSecondaryAddress2() {
		return secondaryAddress2;
	}

	/**
	 * @param secondaryAddress2 the secondaryAddress2 to set
	 */
	public void setSecondaryAddress2(String secondaryAddress2) {
		this.secondaryAddress2 = secondaryAddress2;
	}

	/**
	 * @return the secondaryCity
	 */
	@XmlElement(name="s_city")
	public String getSecondaryCity() {
		return secondaryCity;
	}

	/**
	 * @param secondaryCity the secondaryCity to set
	 */
	public void setSecondaryCity(String secondaryCity) {
		this.secondaryCity = secondaryCity;
	}

	/**
	 * @return the secondaryState
	 */
	@XmlElement(name="s_state")
	public String getSecondaryState() {
		return secondaryState;
	}

	/**
	 * @param secondaryState the secondaryState to set
	 */
	public void setSecondaryState(String secondaryState) {
		this.secondaryState = secondaryState;
	}

	/**
	 * @return the secondaryCountry
	 */
	@XmlElement(name="s_country")
	public String getSecondaryCountry() {
		return secondaryCountry;
	}

	/**
	 * @param secondaryCountry the secondaryCountry to set
	 */
	public void setSecondaryCountry(String secondaryCountry) {
		this.secondaryCountry = secondaryCountry;
	}

	/**
	 * @return the secondaryZipCode
	 */
	@XmlElement(name="s_code")
	public String getSecondaryZipCode() {
		return secondaryZipCode;
	}

	/**
	 * @param secondaryZipCode the secondaryZipCode to set
	 */
	public void setSecondaryZipCode(String secondaryZipCode) {
		this.secondaryZipCode = secondaryZipCode;
	}

	/**
	 * @return the vatName
	 */
	@XmlElement(name="vat_name")
	public String getVatName() {
		return vatName;
	}

	/**
	 * @param vatName the vatName to set
	 */
	public void setVatName(String vatName) {
		this.vatName = vatName;
	}

	/**
	 * @return the vatNumber
	 */
	@XmlElement(name="vat_number")
	public Integer getVatNumber() {
		return vatNumber;
	}

	/**
	 * @param vatNumber the vatNumber to set
	 */
	public void setVatNumber(Integer vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(Double credit) {
		this.credit = credit;
	}

	/**
	 * @return the credit
	 */
	@XmlElement(name="credit", defaultValue="0")
	public Double getCredit() {
		return credit;
	}

	/**
	 * @return the credits
	 */
	@XmlElementWrapper(name="credits")
	@XmlElement(name="credit", defaultValue="0")
	public List<Double> getCredits() {
		return credits;
	}

	/**
	 * @param credits the credits to set
	 */
	public void setCredits(List<Double> credits) {
		this.credits = credits;
	}

	/**
	 * @return the url
	 */
	@XmlElement(name="url")
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the clientUrl
	 */
	@XmlElement(name="auth_url")
	public String getClientUrl() {
		return clientUrl;
	}

	/**
	 * @param clientUrl the clientUrl to set
	 */
	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}

	/**
	 * @return the links
	 */
	@XmlElementWrapper(name="links")
	@XmlElements({
            @XmlElement(name="client_view"),
            @XmlElement(name="view"),
            @XmlElement(name="statement")
         })
	public List<String> getLinks() {
		return links;
	}

	/**
	 * @param links the links to set
	 */
	public void setLinks(List<String> links) {
		this.links = links;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the lastModified
	 */
	@XmlElement(name="updated")
	public String getLastModified() {
		return lastModified;
	}

	/**
	 * @param clientStatus the clientStatus to set
	 */
	public void setClientStatus(ClientStatus clientStatus) {
		this.clientStatus = clientStatus;
	}

	/**
	 * @return the clientStatus
	 */
	@XmlElement(name="folder")
	public ClientStatus getClientStatus() {
		return clientStatus;
	}

	/**
	 * @return the contact
	 */
	@XmlElement(name="contact")
	public Contact getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/**
	 * @return the contacts
	 */
	@XmlElementWrapper(name="contacts")
	@XmlElement(name="contact")
	public List<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	/* (non-Javadoc)
	 * @see timeease.model.AbstractFreshbooksEntity#getFreshbooksObjectName()
	 */
	@Override
	public String getFreshbooksObjectName() {
		return "client";
	}

	/* (non-Javadoc)
	 * @see timeease.model.AbstractFreshbooksEntity#getFreshbooksObjectId()
	 */
	@Override
	public Integer getFreshbooksObjectId() {
		return externalId;
	}
	
	/* (non-Javadoc)
	 * @see timeease.model.IFreshbooksEntity#getFreshbooksObjectLabel()
	 */
	@Override
	public String getFreshbooksObjectLabel() {
		return "Client";
	}

	/* (non-Javadoc)
	 * @see com.monstersoftwarellc.timeease.enums.ILabel#getLabel()
	 */
	@Override
	public String getLabel() {
		return getFirstName() + " " + getLastName();
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
		if(!(obj instanceof Client)){
			return false;
		}
		Client client = (Client) obj;
		
		return new EqualsBuilder()
								.append(client.getId(), getId())
								.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(7, 31)
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
