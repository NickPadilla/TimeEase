/**
 * 
 */
package com.monstersoftwarellc.timeease.integration.freshbooks;

import java.io.File;

import javax.naming.AuthenticationException;
import javax.naming.ConfigurationException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.core.runtime.Assert;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.integration.IRequest;
import com.monstersoftwarellc.timeease.integration.IResponse;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.utility.ConnectionUtility;
import com.monstersoftwarellc.timeease.utility.FreshbooksRequestUtility;
import com.monstersoftwarellc.timeease.utility.FreshbooksResponseUtility;

/**
 * @author navid
 *
 */
public abstract class AbstractRequest<T extends IResponse> implements IRequest<T> {
	
	private File testFile;
	
	private RequestMethods method;
	
	protected Integer page;
	
	protected Integer itemsPerPage;
	
	protected IFreshbooksEntity entity;
	

	/* (non-Javadoc)
	 * @see timeease.model.IRequest#getResponse()
	 */
	@SuppressWarnings("unchecked")
	public T getResponse() throws IllegalStateException, AuthenticationException, ConfigurationException {
		Assert.isNotNull(getEntity(),"IFreshbooks Entitiy Must Not Be Null!");
		Assert.isNotNull(getRequestMethodString(),"RequestMethods Must Not Be Null!");
		if(testFile != null){
			return  (T) FreshbooksResponseUtility.getResponse(getEntity(), testFile);
		}
		String request = FreshbooksRequestUtility.getRequest(this);			
		String responseString = ConnectionUtility.performRequest(request);
		if(responseString.equals("Fail") || responseString.equals("UnknownHost") || responseString.equals("Network Unreachable")){
			return null;
		}
		return  (T) FreshbooksResponseUtility.getResponse(getEntity(), responseString);
	}
	
	/* (non-Javadoc)
	 * @see timeease.model.IRequest#getMethod()
	 */
	@Override
	@XmlTransient
	public RequestMethods getMethod(){
		return method;
	}
	
	/**
	 * @param method
	 */
	public void setMethod(RequestMethods method){
		this.method = method;
	}
	
	/**
	 * @return
	 */
	@XmlAttribute(name="method")
	public String getRequestMethodString() {
		return method.createFullyQualifiedMethodAction(getEntity());
	}

	/**
	 * @param itemsPerPage
	 */
	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
		
	}
	
	/**
	 * @return
	 */
	@XmlElement(name="per_page")
	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * @param page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return
	 */
	@XmlElement(name="page")
	public Integer getPage() {
		return page;
	}
	
	/**
	 * @param testFile the testFile to set
	 */
	public void setTestFile(File testFile) {
		this.testFile = testFile;
	}


	/**
	 * @return the testFile
	 */
	public File getTestFile() {
		return testFile;
	}
	
	/**
	 * @param entity
	 */
	public void setEntity(IFreshbooksEntity entity){
		this.entity = entity;
	}
}
