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

import com.monstersoftwarellc.timeease.search.ISearchCritieraDialog;
import com.monstersoftwarellc.timeease.search.ProjectSearchCritieria;

/**
 * @author nicholas
 * 
 */
public class ProjectSearchCriteriaDialog extends Dialog implements ISearchCritieraDialog {

	@SuppressWarnings("unused")
	private DataBindingContext m_bindingContext;

	private ProjectSearchCritieria criteria;
	private Text nameText;
	
	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public ProjectSearchCriteriaDialog(Shell parentShell, ProjectSearchCritieria criteria) {
		super(parentShell);
		if (criteria == null) {
			criteria = new ProjectSearchCritieria();
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
		newShell.setText("Project Search Criteria Settings");
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
		
		Label lblTaskName = new Label(container, SWT.NONE);
		lblTaskName.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblTaskName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblTaskName.setText("Task Name");
		nameText = new Text(container, SWT.BORDER);
		nameText.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
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
	public ProjectSearchCritieria getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria
	 *            the criteria to set
	 */
	public void setCriteria(ProjectSearchCritieria criteria) {
		this.criteria = criteria;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue nameTextObserveTextObserveWidget = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(nameText, SWT.Modify));
		IObservableValue criteriaNameObserveValue = BeansObservables.observeValue(criteria, "name");
		bindingContext.bindValue(nameTextObserveTextObserveWidget, criteriaNameObserveValue, null, null);
		//
		return bindingContext;
	}
}
