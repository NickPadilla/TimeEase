/**
 * 
 */
package com.monstersoftwarellc.timeease.test.requestxml;

import java.io.StringWriter;
import java.util.Date;

import com.monstersoftwarellc.timeease.model.entry.EntryRequest;
import com.monstersoftwarellc.timeease.model.enums.RequestMethods;
import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Task;
import com.monstersoftwarellc.timeease.utility.FreshbooksRequestUtility;
import com.monstersoftwarellc.timeease.utility.TimeUtil;

/**
 * @author nick
 *
 */
public class EntryXmlTest {
	
	public static void main(String[] args) {
		Entry entry = getEntry();
        StringWriter sw = new StringWriter();
		for(RequestMethods method : RequestMethods.values()){
			// note: only ever set the ID on objects when we already know the id
			if(method != RequestMethods.CREATE)entry.setExternalId(1L);
			EntryRequest request = getEntryRequest(entry);
			request.setMethod(method);
			sw.append(FreshbooksRequestUtility.getRequest(request));	
		}
		System.out.println(sw.getBuffer());
	}

	private static Entry getEntry() {
		Entry entry = new Entry();
		entry.setEntryDate(new Date());
		entry.setHours(4.0);
		entry.setNotes("Entry Note!");
		entry.setProject(new Project());
		entry.setTask(new Task());
		return entry;
	}
	
	private static EntryRequest getEntryRequest(Entry entry){
		EntryRequest request = new EntryRequest();
		request.setItemsPerPage(15);
		request.setPage(1);
		request.setFrom(TimeUtil.formatDate(new Date().getTime()));
		request.setTo(TimeUtil.formatDate(new Date().getTime()));
		request.setProjectId(9);
		request.setTaskId(10);
		request.setMethod(RequestMethods.CREATE);
		request.setEntity(entry);
		return request;
	}

}
