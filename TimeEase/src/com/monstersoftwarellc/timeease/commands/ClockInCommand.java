/**
 * 
 */
package com.monstersoftwarellc.timeease.commands;

import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import com.monstersoftwarellc.timeease.model.impl.Entry;
import com.monstersoftwarellc.timeease.service.IEntryService;
import com.monstersoftwarellc.timeease.service.ISecurityService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;

/**
 * @author nicholas
 *
 */
public class ClockInCommand extends AbstractHandler {
	
	public final static String ID = "com.monstersoftwarellc.timeease.commands.ClockInCommand";

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// just clock in and save the entry 
		// do we want to add an option in the settings that says to 
		// show the options dialog on clock in as well?
		// we will always show it when clocking out.
		// ensure we set the entryDate to the current date
		Entry entry = new Entry();
		Date now = new Date();
		entry.setEntryDate(now);
		entry.setHours(0d);
		entry.setNotes("Clocked In");
		entry.setStartTime(now);
		entry.setAccount(ServiceLocator.locateCurrent(ISecurityService.class).getCurrentlyLoggedInUser());
		// persist new entry
		ServiceLocator.locateCurrent(IEntryService.class).getEntryRepository().saveAndFlush(entry);
		// spark an event so that we re-evaluate the command bindings
				IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
				// get the service 
				ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		// get our source provider by querying by the variable name 
		ClockingCommandState clockingCommandState = (ClockingCommandState) service.getSourceProvider(ClockingCommandState.ID); 
		// set the value 
		clockingCommandState.setIn(); 
		return null;
	}

}
