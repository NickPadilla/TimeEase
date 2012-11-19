/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import java.util.ArrayList;

import com.monstersoftwarellc.timeease.integration.GeneralResponse;
import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.impl.Client;

/**
 * Utility for getting the correct set of classes needed so JaxB<br/>
 * can do its work.
 * @author nick
 *
 */
public class JaxbXmlClassUtility {
	
	/**
	 * Gets the correct array of classes needed to marshal and <br/>
	 * unmarshal the given {@link IFreshbooksEntity}.
	 * @param clazz
	 * @return
	 */
	public static Class<?>[] getXmlClassesForClass(IFreshbooksEntity clazz){

		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		//list.addAll(Arrays.asList(declareGeneralResponseXmlClasses()));
		
		if(clazz != null && !(clazz instanceof GeneralResponse)){
			if(clazz instanceof Client){
				/*list.addAll(Arrays.asList(declareClientXmlClasses()));
			}else if(clazz instanceof Entry){
				list.addAll(Arrays.asList(declareEntryXmlClasses()));
			}else if(clazz instanceof Project){
				list.addAll(Arrays.asList(declareProjectXmlClasses()));
			}else if(clazz instanceof Task){
				list.addAll(Arrays.asList(declareTaskXmlClasses()));*/
			}else{
				throw new IllegalStateException("New Freshbook Object Needs To Be Added To JaxbXmlClassUtility! "
						+ "\n    class: " + clazz);
			}
		}
		
		Class<?>[] classArray = new Class<?>[list.size()];
		list.toArray(classArray);		
		
		return classArray;
	}
	
	/*public static Class<?>[] declareClientXmlClasses() {
		return new Class[]{Client.class,ClientRequest.class,ClientListResponse.class,ClientResponse.class};
	}
	
	public static Class<?>[] declareEntryXmlClasses() {
		return new Class[]{Entry.class,EntryRequest.class,EntryResponse.class,EntryListResponse.class};
	}

	public static Class<?>[] declareProjectXmlClasses() {
		return new Class[]{Project.class,Task.class,ProjectRequest.class,ProjectResponse.class,ProjectListResponse.class};
	}
	
	public static Class<?>[] declareTaskXmlClasses() {
		return new Class[]{Task.class,TaskRequest.class,TaskListResponse.class,TaskResponse.class};
	}
	
	public static Class<?>[] declareGeneralResponseXmlClasses(){
		return new Class[]{GeneralResponse.class,EntityRequest.class,ArrayList.class};
	}*/
}
