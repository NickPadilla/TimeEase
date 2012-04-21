/**
 * 
 */
package com.monstersoftwarellc.timeease.utility;

import com.monstersoftwarellc.timeease.TimeEaseSession;


/**
 * @author nicholas
 *
 */
public class SyncFreshbooksStateUtility {
	
	/**
	 * Method responsible for handling the sync operations. Must handle these scenarios: <br/>
	 * <b>1.</b>  Send dirty objects to FreshBooks. <br/>
	 * <b>2.</b>  Update internal lists, after successful update, with fresh data. <br/>
	 * <b>3.</b>  May have internet problems in between sends, need to ensure we fail gracefully.<br/>
	 * 			  If network is lost we stop the process ensure we are marking sent items as not dirty <br/>
	 * 			  and also set the id returned from the server. <br/>
	 * <b>4.</b>  Set the updated list to the session.<br/>
	 * <b>5.</b>  Return TRUE if in fact everything hapened no problem, or false we had a problem.<br/>
	 * <b>6.</b>  Need to also sync<br/><br/>
	 * <b>NOTE:</b> Assumes that there is an internet connection, if there is no connection we don't do anything.
	 * TODO: Create a class to handle this, TimeEaseConnections or something. 
	 * TODO: Create a metadata class that we can return with the needed information.
	 * idea: we need to ensure we know what connection
	 * @return updated list 
	 */
	public static void sync(){
		// see if we are online, just doing this check will suffice; if offline methods will get from disk
		ConnectionUtility.areWeOnline();

		// after we update the CPTL from the server we send over any entry that hasn't been sent already.
		if(TimeEaseSession.getCurrentInstance().isOnline()){
			// if we are online then we get the dirty stuff from the database and send that.
		}
	}

}
