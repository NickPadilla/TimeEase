package com.monstersoftwarellc.timeease;

import java.net.URL;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.equinox.security.auth.ILoginContext;
import org.eclipse.equinox.security.auth.LoginContextFactory;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

import com.monstersoftwarellc.timeease.utility.ConnectionUtility;
import com.monstersoftwarellc.timeease.utility.TimeEaseLicenseUtility;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	public static final String PLUGIN_ID = "com.monstersoftwarellc.timeease";

	private static final String JAAS_CONFIG_FILE = "config/jaas_config.txt";

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		// setup database - will populate test data only if we pass in true - false for production
//		ServiceLocator.locateCurrent(ICreateDatabaseForTesting.class).createTestUpdateDatabase(true);

		ConnectionUtility.areWeOnline();

		String configName = "JAAS";
		BundleContext bundleContext = context.getBrandingBundle().getBundleContext();
		URL configUrl = bundleContext.getBundle().getEntry(JAAS_CONFIG_FILE);
		ILoginContext secureContext = LoginContextFactory.createContext(configName, configUrl);

		try {
			secureContext.login();
		} catch (LoginException e) {
			IStatus status = new Status(IStatus.ERROR, PLUGIN_ID,
					"Login failed", e);
			ErrorDialog.openError(null, "Error", "Login failed", status);
		}

		// if we don't have a valid license then we don't 
		// even try to login.
		if(!licenseValid()){
			return IApplication.EXIT_OK;
		}

		Integer result = null;
		try {
			result = (Integer) Subject.doAs(secureContext.getSubject(),
					getRunAction(display));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			display.dispose();
			secureContext.logout();
		}

		return result;
	}

	/**
	 * @param display
	 * @return
	 */
	private PrivilegedAction<?> getRunAction(final Display display) {
		return new PrivilegedAction<Object>() {

			public Object run() {
				int result = PlatformUI.createAndRunWorkbench(display,
						new ApplicationWorkbenchAdvisor());
				return new Integer(result);
			}
		};
	}
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		if (!PlatformUI.isWorkbenchRunning()){
			return;
		}
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}

	private boolean licenseValid() {
		boolean valid = true;
		if(TimeEaseLicenseUtility.pastThirtyDayTrialPeriod()){
			valid = false;
		}
		// if false then we know we are past 30 days
		// check for license key
		if(!valid){
			valid = TimeEaseLicenseUtility.isLicenseValid();
			if(!valid){
				MessageDialog.openError(null, "License Invalid!", "License is not valid! Passed 30 day trial period, please purchase a license key at:" +
						"\n http://timeease.monstersoftwarellc.com/purchase");
			}
		}
		return valid;
	}

}