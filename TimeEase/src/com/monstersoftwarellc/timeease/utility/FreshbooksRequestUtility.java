/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.eclipse.core.runtime.Assert;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.integration.freshbooks.AbstractRequest;
import com.monstersoftwarellc.timeease.integration.freshbooks.EntityRequest;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;

/**
 * 
 * This utility will be used to marshal out the request strings that freshbooks expects.<br/>
 * Also this utility will handle the unmarshaling of the freshbooks response.
 * @author nick
 *
 */
public class FreshbooksRequestUtility {
	
	private FreshbooksRequestUtility(){}
	
	/**
	 * This method will return a {@link String} instance that represents the <br/>
	 * freshbooks request for the given {@link IFreshbooksEntity} data object.
	 * <br/>
	 * <b>NOTE: {@link RequestMethods}.LIST is NOT Allowed! This is because Lists <br/>
	 * operations are handled differently.  If i can find a way so that it doesn't compile<br/>
	 * when LIST is used i will fix this.  Will throw an Assertion error if List is used.<br/>
	 * If you need to make a LIST call please use <br/> 
	 * {@link FreshbooksRequestUtility}.getListRequest().</b>
	 * @param object
	 * @param method
	 * @return
	 */
	public static String getRequest(AbstractRequest<?> abstractRequest){
		Assert.isNotNull(abstractRequest);
		String requestString = "";
        try {        	
        	StringWriter writer = new StringWriter();        	
        	if(abstractRequest.getMethod() == RequestMethods.LIST){
        		getWriterForListRequest(abstractRequest, writer);
        	}else{
        		EntityRequest entityRequest = createRequest(abstractRequest);
        		getWriterForRequest(entityRequest, writer);
        	}
            // get and delete only ever need the object id.
            if(abstractRequest.getMethod() == RequestMethods.DELETE || abstractRequest.getMethod() == RequestMethods.GET){
            	requestString = modifyRequestForDeleteGetOperations(abstractRequest.getEntity(), writer.getBuffer());
            }else{
            	requestString = writer.toString();
            }
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return requestString;
	}

	/**
	 * Does a replace on the given string so that it is correctly formatted for
	 * the Delete Get operations.
	 * @param object
	 * @param stringBuffer
	 * @return
	 */
	private static String modifyRequestForDeleteGetOperations(IFreshbooksEntity object, StringBuffer stringBuffer) {
		int start = stringBuffer.indexOf("<"+object.getFreshbooksObjectName()+">");
		int end = stringBuffer.indexOf("</"+object.getFreshbooksObjectName()+">")+(object.getFreshbooksObjectName().length()+3);
		StringBuffer request = stringBuffer.replace(start, end, getFreshbooksEntityId(object));
		return request.toString();
	}
	
	/**
	 * Returns the xml formatted object id, for example you would get this when passed the Entry object. </br>
	 * {@literal <time_entry>1</time_entry>}.
	 * @param object
	 * @return
	 */
	private static String getFreshbooksEntityId(IFreshbooksEntity object){
		String startObjectId = "<" + object.getFreshbooksObjectName();
		String endObjectId = "</" + object.getFreshbooksObjectName();
		return startObjectId + "_id>" + object.getFreshbooksObjectId() + endObjectId + "_id>"; 
	}
	
	/**
	 * This method sets the {@link StringWriter} with the request data.
	 * @param request
	 * @param writer
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private static void getWriterForListRequest(AbstractRequest<?> request, StringWriter writer) 
			throws JAXBException, PropertyException {		
		JAXBContext context = JAXBContext.newInstance(JaxbXmlClassUtility.getXmlClassesForClass(request.getEntity()));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(request, writer);
	}

	/**
	 * This method sets the {@link StringWriter} with the request data.
	 * @param request
	 * @param writer
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private static void getWriterForRequest(EntityRequest request, StringWriter writer)
			throws JAXBException, PropertyException {
		JAXBContext context = JAXBContext.newInstance(JaxbXmlClassUtility.getXmlClassesForClass(request.getFreshbooksEntity()));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(request, writer);
	}

	/**
	 * Creates {@link EntityRequest} from the given data object and {@link RequestMethods} type.
	 * @param object
	 * @param method
	 * @return
	 */
	private static EntityRequest createRequest(AbstractRequest<?> abstractRequest) {
		EntityRequest request = new EntityRequest();
		request.setFreshbooksEntity(abstractRequest.getEntity());
		request.setMethod(abstractRequest.getRequestMethodString());
		return request;
	}
}
