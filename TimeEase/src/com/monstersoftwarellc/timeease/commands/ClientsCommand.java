/**
 * 
 */
package com.monstersoftwarellc.timeease.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.monstersoftwarellc.timeease.view.ClientsView;


/**
 * @author nicholas
 *
 */
public class ClientsCommand extends AbstractHandler implements IHandler {
	
	public static final String ID = "com.monstersoftwarellc.timeease.commands.ClientsCommand";

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		try {
			page.showView(ClientsView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

}
