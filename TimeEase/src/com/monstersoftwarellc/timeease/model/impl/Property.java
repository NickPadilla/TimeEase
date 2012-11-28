package com.monstersoftwarellc.timeease.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Index;
import org.hibernate.validator.constraints.NotEmpty;

import com.monstersoftwarellc.timeease.model.BaseDomain;
import com.monstersoftwarellc.timeease.model.PropertyType;
import com.monstersoftwarellc.timeease.utility.SerialUtil;

@Entity
public class Property extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6069489899248153329L;

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
    
    @Index
    @Column(length=2000)
	@NotEmpty
    private String propertyName;
    private String label;
// 	@Column(length=20000000, columnDefinition="longblob") // mysql/h2
    @Column(length=20000000) // derby
 	@Lob
    private byte[] propertyValue;
//	@Column(length=20000000, columnDefinition="longblob",nullable=true) // mysql/h2
    @Column(length=20000000, nullable=true) // derby
	@Lob
    private byte[] propertyChoice;
    private Boolean propertyHidden = false;
    // if property should be shared with the central server.
    private Boolean propertyShared = true;
    @Enumerated(EnumType.STRING)
	@Index
    private PropertyType propertyType;
	
    
	public Property() {
		
    }	

	
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
    
    /** These methods should be used by external classes to access property values ***/
    @Transient
    public Object getValue(){
    	Object ret = null;
    	if(propertyValue != null && propertyValue.length > 0){
    		ret = SerialUtil.deSerializeObjectFromBytes(propertyValue);
    	}
    	return ret;
    }
    
    public void setValue(Object value){
    	propertyValue = SerialUtil.serializeObjectToBytes(value);
    }
    
    @Transient
    public Object getChoice(){
    	return (propertyChoice != null && propertyChoice.length > 0 ? SerialUtil.deSerializeObjectFromBytes(propertyChoice):null);
    }
    
    public void setChoice(Object value){
    	propertyChoice = (value != null ? SerialUtil.serializeObjectToBytes(value):null);
    }
    
	public String getPropertyName() {
		return propertyName;
	}


	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public byte[] getPropertyValue() {
		return propertyValue;
	}


	public void setPropertyValue(byte[] propertyValue) {
		this.propertyValue = propertyValue;
	}


	public byte[] getPropertyChoice() {
		return propertyChoice;
	}


	public void setPropertyChoice(byte[] propertyChoice) {
		this.propertyChoice = propertyChoice;
	}


	public Boolean getPropertyHidden() {
		return propertyHidden;
	}


	public void setPropertyHidden(Boolean propertyHidden) {
		this.propertyHidden = propertyHidden;
	}


	public PropertyType getPropertyType() {
		return propertyType;
	}


	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}


	public Boolean getPropertyShared() {
		return propertyShared;
	}


	public void setPropertyShared(Boolean shared) {
		this.propertyShared = shared;
	}

}


