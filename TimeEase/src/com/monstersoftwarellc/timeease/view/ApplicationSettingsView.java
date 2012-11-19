/**
 * 
 */
package com.monstersoftwarellc.timeease.view;

import java.beans.Beans;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.monstersoftwarellc.timeease.property.IApplicationSettings;
import com.monstersoftwarellc.timeease.property.IPropertyProxy;
import com.monstersoftwarellc.timeease.property.ui.PropertyComposite;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;



/**
 * @author nicholas
 * 
 */
public class ApplicationSettingsView extends ViewPart {

	@SuppressWarnings("unused")
	private DataBindingContext m_bindingContext;

	public static final String ID = "com.monstersoftwarellc.timeease.view.ApplicationSettingsView"; //$NON-NLS-1$

	private IApplicationSettings settings = null;
	

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		if(!Beans.isDesignTime()){
			settings = ServiceLocator.locateCurrent(IApplicationSettings.class);
		}
		
		
		PropertyComposite composite = new PropertyComposite(scrolledComposite,SWT.NONE,(IPropertyProxy)settings);
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	
	}

	//FIXME: add handlers that are called after value is set for properties. 
	public class ApplicationSettingsUpdateStrategy extends UpdateValueStrategy {

		/**
		 * 
		 */
		public ApplicationSettingsUpdateStrategy() {
			super(UpdateValueStrategy.POLICY_UPDATE);
		}

		protected IStatus doSet(IObservableValue observableValue, Object value) {
			IStatus ret = super.doSet(observableValue, value);
			if (ret.isOK()) {
				Realm.getDefault().asyncExec(new Runnable() {
					public void run() {
						// Update after setting
						// ServiceLocator.locateCurrent(ISyncService.class).updateSyncProperties();
					}
				});
			}
			return ret;
		}

	}

	@Override
	public void setFocus() {
		// Set the focus
	}
}
