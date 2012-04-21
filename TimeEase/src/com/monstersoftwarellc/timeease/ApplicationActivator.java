/**
 * 
 */
package com.monstersoftwarellc.timeease;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author nicholas
 *
 */
public class ApplicationActivator extends AbstractUIPlugin {
	
	// Shared instance of bundle context
	private static BundleContext bundleContext;
	
	// The shared instance
	private static ApplicationActivator plugin;
	

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		bundleContext = context;
		plugin = this;
	}



	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(Application.PLUGIN_ID, path);
	}

	/**
	 * @return the bundleContext
	 */
	public static BundleContext getBundleContext() {
		return bundleContext;
	}
	
	public static ApplicationActivator getDefault() {
		return plugin;
	}

}
