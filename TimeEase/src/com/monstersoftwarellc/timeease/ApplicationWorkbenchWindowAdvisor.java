package com.monstersoftwarellc.timeease;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.internal.p2.core.helpers.LogHelper;
import org.eclipse.equinox.internal.p2.core.helpers.ServiceHelper;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.monstersoftwarellc.timeease.model.enums.Images;
import com.monstersoftwarellc.timeease.utility.P2Util;

@SuppressWarnings("restriction")
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {
	
	private IWorkbenchWindow window;
	
	private Image statusImage;

	private TrayItem trayItem;

	private Image trayImage;

	private ApplicationActionBarAdvisor actionBarAdvisor;

	private static final String JUSTUPDATED = "justUpdated";
	
	public ApplicationWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		actionBarAdvisor = new ApplicationActionBarAdvisor(configurer);
		return actionBarAdvisor;
	}
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 800));
        configurer.setShowStatusLine(true);
    }

    public void dispose() {
		if(statusImage != null){
			statusImage.dispose();
		}
		if(trayImage != null){
			trayImage.dispose();
		}
		if(trayItem != null){
			trayItem.dispose();
		}
	}

	public void postWindowOpen() {
		super.postWindowOpen();
		window = getWindowConfigurer().getWindow();
		trayItem = initTaskItem(window);
		if (trayItem != null) {
			hookResize();
			hookPopupMenu();
		}

		performUpdate();
		
	}
	
	/**
	 * Have to do the update here as we need to have the display already created to show progress dialog. I'll have to
	 * discuss this with navid to ensure the workflow will jive with the auto updater. 
	 */
	private void performUpdate() {
		final IProvisioningAgent agent = (IProvisioningAgent) ServiceHelper.getService(ApplicationActivator.getBundleContext(),
				IProvisioningAgent.SERVICE_NAME);
		if (agent == null) {
			LogHelper
			.log(new Status(IStatus.ERROR, Application.PLUGIN_ID,
					"No provisioning agent found.  This application is not set up for updates."));
		}
		// XXX if we're restarting after updating, don't check again.
		final IPreferenceStore prefStore = ApplicationActivator.getDefault().getPreferenceStore();
		if (prefStore.getBoolean(JUSTUPDATED)) {
			prefStore.setValue(JUSTUPDATED, false);
			return;
		}

		// XXX check for updates before starting up.
		// If an update is performed, restart. Otherwise log
		// the status.
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				IStatus updateStatus = P2Util.checkForUpdates(agent, monitor);
				if (updateStatus.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
					PlatformUI.getWorkbench().getDisplay()
					.asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openInformation(null,
									"Updates", "No updates were found");
						}
					});
				} else if (updateStatus.getSeverity() != IStatus.ERROR) {
					prefStore.setValue(JUSTUPDATED, true);
					PlatformUI.getWorkbench().restart();
				} else {
					LogHelper.log(updateStatus);
				}
			}
		};
		try {
			new ProgressMonitorDialog(Display.getCurrent().getActiveShell()).run(true, true, runnable);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		}
	}

	private void hookResize() {
		window.getShell().addShellListener(new ShellAdapter() {
			public void shellIconified(ShellEvent e) {
				window.getShell().setVisible(false);
				window.getShell().setMinimized(true);
			}
		});
		// If user double-clicks on the tray icons the application will be
		// visible again
		trayItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = window.getShell();
				if (!shell.isVisible()) {
					shell.setVisible(true);
					window.getShell().setMinimized(false);
				}else{
					shell.setVisible(false);
					window.getShell().setMinimized(true);
				}
			}
		});
	}

	private void hookPopupMenu() {
		// Add listener for menu pop-up
		trayItem.addListener(SWT.MenuDetect, new Listener() {
			public void handleEvent(Event event) {
				MenuManager trayMenu = new MenuManager();
				Menu menu = trayMenu.createContextMenu(window.getShell());
				actionBarAdvisor.fillTrayItem(trayMenu);
				menu.setVisible(true);
			}
		});
	}

	private TrayItem initTaskItem(IWorkbenchWindow window) {
		final Tray tray = window.getShell().getDisplay().getSystemTray();
		if (tray == null){
			return null;
		}
		trayItem = new TrayItem(tray, SWT.NONE);
		trayImage = AbstractUIPlugin.imageDescriptorFromPlugin(Application.PLUGIN_ID, Images.TASK_TRAY.getPath()).createImage();
		trayItem.setImage(trayImage);
		trayItem.setToolTipText("Time Ease");
		return trayItem;
	}
	
}
