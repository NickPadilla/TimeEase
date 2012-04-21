package com.monstersoftwarellc.timeease;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.monstersoftwarellc.timeease.model.enums.Images;


/**
 * @author nick
 *
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
	private IWorkbenchAction exitAction;
	private IWorkbenchAction aboutAction;
	private IWorkbenchAction helpAction;
	
	// TODO: add Clock In/Out Commands to the system tray
	
	private static StatusLineContribution statusContribution;
	
	private static ImageRegistry images = new ImageRegistry();
    /**
     * @param configurer
     */
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.application.ActionBarAdvisor#makeActions(org.eclipse.ui.IWorkbenchWindow)
     */
    @Override
    protected void makeActions(IWorkbenchWindow window) {
    	exitAction = ActionFactory.QUIT.create(window);
    	register(exitAction);
    	aboutAction = ActionFactory.ABOUT.create(window);
    	register(aboutAction);
    	helpAction = ActionFactory.HELP_CONTENTS.create(window);
    	register(helpAction);
    	
    	statusContribution = new StatusLineContribution("statusline", 20);
    	statusContribution.addListener(0, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				setStatusLineForOnlineOffline();
			}
		});
    	TimeEaseSession.getCurrentInstance().setStatusLineContribution(statusContribution);
    	setStatusLineForOnlineOffline();
    }
    

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
		IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle()
				| SWT.BOTTOM);
		coolBar.add(toolbar);
	}

	/**
	 * @param trayItem
	 */
	protected void fillTrayItem(IMenuManager trayItem) {
		trayItem.add(helpAction);
		trayItem.add(aboutAction);
		trayItem.add(exitAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.application.ActionBarAdvisor#fillStatusLine(org.eclipse
	 * .jface.action.IStatusLineManager)
	 */
	@Override
	protected void fillStatusLine(IStatusLineManager statusLine) {
		statusLine.add(statusContribution);
	}
	
	/**
	 * Method uses the {@link TimeEaseSession} to check to see if we are online,
	 * then updates the status line.
	 */
	public void setStatusLineForOnlineOffline() {
		String areWeOnline = "";
		Images statusImage = null;
		if(TimeEaseSession.getCurrentInstance().isOnline()){
			areWeOnline = "Online";
			statusImage = Images.ONLINE;
		}else{
			areWeOnline = "Offline";
			statusImage = Images.OFF_LINE;
		}
		Image image = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, statusImage.getPath()).createImage();
		updateStatusLine(image, areWeOnline);
	}
	
	/**
	 * @param imageEnum
	 * @param message
	 */
	private void updateStatusLine(final Image image, final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				statusContribution.setText(message);
				statusContribution.setImage(image);
			}
		});
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.application.ActionBarAdvisor#dispose()
	 */
	public void dispose() {
		images.dispose();
	}
}
