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

import com.monstersoftwarellc.timeease.TimeEaseSession;
import com.monstersoftwarellc.timeease.view.ReportABugEnhancementView;

/**
 * @author nicholas
 *
 */
public class ReportABugEnhancementCommand extends AbstractHandler implements IHandler {
	
	public final static String ACTION_ID = "com.monstersoftwarellc.timeease.commands.ReportABugEnhancementCommand";

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		try {
			page.showView(ReportABugEnhancementView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 */
	@Override
	public void setEnabled(Object evaluationContext) { 
		setBaseEnabled(TimeEaseSession.getCurrentInstance().isOnline());
	}

}
