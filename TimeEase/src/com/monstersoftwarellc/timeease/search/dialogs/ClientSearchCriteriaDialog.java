/**
 * 
 */
package com.monstersoftwarellc.timeease.search.dialogs;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.monstersoftwarellc.timeease.search.ClientSearchCritieria;
import com.monstersoftwarellc.timeease.search.ISearchCritieraDialog;

/**
 * @author nicholas
 * 
 */
public class ClientSearchCriteriaDialog extends Dialog implements ISearchCritieraDialog {

	@SuppressWarnings("unused")
	private DataBindingContext m_bindingContext;

	private ClientSearchCritieria criteria;
	private Text firstNameText;
	private Text lastNameText;
	private Text organizationText;
	
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ClientSearchCriteriaDialog(Shell parentShell, ClientSearchCritieria criteria) {
		super(parentShell);
		if (criteria == null) {
			criteria = new ClientSearchCritieria();
		}
		this.setCriteria(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Client Search Criteria Settings");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(2, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		Label lblClientFirstName = new Label(container, SWT.NONE);
		lblClientFirstName.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblClientFirstName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblClientFirstName.setText("Client First Name");
		firstNameText = new Text(container, SWT.BORDER);
		firstNameText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		firstNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblClientLastName = new Label(container, SWT.NONE);
		lblClientLastName.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblClientLastName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblClientLastName.setText("Client Last Name");
		lastNameText = new Text(container, SWT.BORDER);
		lastNameText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lastNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblClientOrg = new Label(container, SWT.NONE);
		lblClientOrg.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblClientOrg.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblClientOrg.setText("Client Organization");
		organizationText = new Text(container, SWT.BORDER);
		organizationText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		organizationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,true);
		m_bindingContext = initDataBindings();
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(483, 389);
	}

	/**
	 * @return the criteria
	 */
	public ClientSearchCritieria getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria
	 *            the criteria to set
	 */
	public void setCriteria(ClientSearchCritieria criteria) {
		this.criteria = criteria;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue firstNameTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(firstNameText, SWT.Modify));
		IObservableValue criteriaFirstNameObserveValue = BeansObservables.observeValue(criteria, "firstName");
		bindingContext.bindValue(firstNameTextObserveTextObserveWidget, criteriaFirstNameObserveValue, null, null);
		//
		IObservableValue lastNameTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(lastNameText, SWT.Modify));
		IObservableValue criteriaLastNameObserveValue = BeansObservables.observeValue(criteria, "lastName");
		bindingContext.bindValue(lastNameTextObserveTextObserveWidget, criteriaLastNameObserveValue, null, null);
		//
		IObservableValue organizationTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(organizationText, SWT.Modify));
		IObservableValue criteriaOrganizationObserveValue = BeansObservables.observeValue(criteria, "organization");
		bindingContext.bindValue(organizationTextObserveTextObserveWidget, criteriaOrganizationObserveValue, null, null);
		return bindingContext;
	}
}
