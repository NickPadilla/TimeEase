/**
 * 
 */
package com.monstersoftwarellc.timeease.model.enums;

import com.monstersoftwarellc.timeease.integration.IFreshbooksEntity;
import com.monstersoftwarellc.timeease.model.impl.Client;
import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.model.impl.Project;
import com.monstersoftwarellc.timeease.model.impl.Task;

/**
 * @author nick
 *
 */
public enum Images {

	CLIENT("icons/client.gif"),
	PROJECT("icons/project.gif"),
	TASK("icons/task.gif"),
	ENTRY("icons/money.png"),
	TASK_TRAY("icons/32.png"),
	TIME("icons/time_go.png"),
	TIME_START("icons/clock_play.png"),
	TIME_STOP("icons/clock_stop.png"),
	ONLINE("icons/accept.png"),
	OFF_LINE("icons/cancel.png"),
	SYNC("icons/arrow_refresh.png"),
	REPORT_REQUEST("icons/report_add.png");
	
	private String path;
	
	private Images(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
	public static String getImageLocationFromIFreshbooksEntity(IFreshbooksEntity entity){
		Images image = null;
		if(entity instanceof Client){
			image = Images.CLIENT;
		}else if(entity instanceof Project){
			image = Images.PROJECT;
		}else if(entity instanceof Task){
			image = Images.TASK;
		}else if(entity instanceof Entry){
			image = Images.ENTRY;
		}else{
			throw new IllegalStateException("You have tried to access an Image for an object that doesn't exist! Please add new IFreshbooksEntity to Images enum!");
		}
		return image.path;
	}
}
