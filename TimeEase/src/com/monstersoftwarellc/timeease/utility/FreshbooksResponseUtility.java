/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import org.eclipse.core.runtime.Assert;

import com.monstersoftwarellc.timeease.integration.GeneralResponse;
import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractListResponse;
import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractResponse;
import com.monstersoftwarellc.timeease.model.enums.ResponseStatus;

/**
 * Utility that supplies the functionality needed for handling Freshbooks response. 
 * @author nick
 *
 */
public class FreshbooksResponseUtility {

	private FreshbooksResponseUtility(){}

	/**
	 * Get the response object for the given {@link IDeclareClassesForXml} and <br/>
	 * xml <code>file</code>.  {@link File} must be in UTF-8 encoding. 
	 * @param response
	 * @param file
	 * @return
	 */
	public static Object getResponse(AbstractResponse response, File file){
		Assert.isNotNull(file,"File Must Not Be Null!");
		return getObjectResponse(response.getResponseEntity(),null,file);
	}
	
	/**
	 * Get the response object for the given {@link IFreshbooksEntity} and <br/>
	 * xml <code>responseString</code>.  The xml must be in UTF-8 encoding.
	 * @param response
	 * @param responseString
	 * @return
	 */
	public static Object getResponse(IFreshbooksEntity entity, String responseString){
		return getObjectResponse(entity,responseString,null);
	}
	
	/**
	 * Get the response object for the given {@link IFreshbooksEntity} and <br/>
	 * xml <code>responseFile</code>.  The xml must be in UTF-8 encoding.
	 * @param response
	 * @param responseFile
	 * @return
	 */
	public static Object getResponse(IFreshbooksEntity entity, File responseFile){
		return getObjectResponse(entity,null,responseFile);
	}

	/**
	 * Pass in the file name for the file to look for and the type of object the file represents, if found 
	 * we will parse it and return the {@link AbstractListResponse}for the given {@link IFreshbooksEntity}. 
	 * @param fileName
	 * @param entity
	 * @return
	 */
	public static AbstractListResponse getCollectionFromFile(String fileName, IFreshbooksEntity entity){
		File file = new File(fileName);
		return (AbstractListResponse) getObjectResponse(entity, null, file );
		
	}

	
	/**
	 * This private method will get the correct object for the given {@link IFreshbooksEntity}
	 * and either <code>responseString</code> or <code>responseFile</code>.
	 * @param response
	 * @param responseString
	 * @param responseFile
	 * @return
	 */
	private static Object getObjectResponse(IFreshbooksEntity entity, String responseString, File responseFile){
		Object returnObject = null;
		try {
			
			JAXBContext jc = JAXBContext.newInstance(JaxbXmlClassUtility.getXmlClassesForClass(entity));
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			// this is used more for trouble shooting problems with the process.
			unmarshaller.setEventHandler(new DefaultValidationEventHandler());
			if(responseFile == null){
				returnObject = unmarshaller.unmarshal(new ByteArrayInputStream(responseString.getBytes()));
			}else{
				returnObject = unmarshaller.unmarshal(responseFile);
			}
			
			if(returnObject instanceof GeneralResponse){
				
				if(((GeneralResponse)returnObject).getStatus() == ResponseStatus.FAILED){
					throw new IllegalStateException("Freshbooks Responded With a Failed Status! "
														+ "\n   error message:    " + ((GeneralResponse)returnObject).getError());
				}
			}
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
		return returnObject;
	}
}
