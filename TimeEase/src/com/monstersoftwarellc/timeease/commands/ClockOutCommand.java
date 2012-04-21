/**
 * 
 */
package com.monstersoftwarellc.timeease.commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.ISourceProviderService;

import com.monstersoftwarellc.timeease.dao.IEntryDAO;
import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.model.WhereClause;
import com.monstersoftwarellc.timeease.model.entry.Entry;
import com.monstersoftwarellc.timeease.service.ServiceLocator;
import com.monstersoftwarellc.timeease.utility.TimeUtil;

/**
 * @author nicholas
 *
 */
public class ClockOutCommand extends AbstractHandler {

	public final static String ID = "com.monstersoftwarellc.timeease.commands.ClockOutCommand";

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// here just clock out and set the hours
		// here we also need to present a dialog that gives them
		// the ability to update the entry from the defaults.
		List<WhereClause> clauses = new ArrayList<WhereClause>();
		clauses.add(new WhereClause("startTime", null, SearchOperators.NOT_EQUAL_TO));
		clauses.add(new WhereClause("endTime", null, SearchOperators.EQUAL_TO));
		List<Entry> entries = ServiceLocator.locateCurrent(IEntryDAO.class).findAllOrderBy(clauses, true, "entryDate");
		Entry entry = entries.get(0);
		Date now = new Date();
		entry.setEndTime(now);
		entry.setHours(TimeUtil.getDuration(entry.getStartTime().getTime(), now.getTime()));
		entry.setNotes(entry.getNotes() + " - Clocked Out");
		// persist new entry
		ServiceLocator.locateCurrent(IEntryDAO.class).merge(entry);
		// spark an event so that we re-evaluate the command bindings
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event); 
		// get the service 
		ISourceProviderService service = (ISourceProviderService) window.getService(ISourceProviderService.class); 
		// get our source provider by querying by the variable name 
		ClockingCommandState clockingCommandState = (ClockingCommandState) service.getSourceProvider(ClockingCommandState.ID); 
		// set the value 
		clockingCommandState.setOut(); 
		return null;
	}

}
