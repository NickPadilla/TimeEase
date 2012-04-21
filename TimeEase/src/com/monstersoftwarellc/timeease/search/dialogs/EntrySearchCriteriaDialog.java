/**
 * 
 */
package com.monstersoftwarellc.timeease.search.dialogs;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import com.monstersoftwarellc.timeease.enums.SearchOperators;
import com.monstersoftwarellc.timeease.search.EntrySearchCritieria;
import com.monstersoftwarellc.timeease.search.ISearchCritieraDialog;
import com.monstersoftwarellc.timeease.widgets.CustomLabelProvider;

/**
 * @author nicholas
 * 
 */
public class EntrySearchCriteriaDialog extends Dialog implements ISearchCritieraDialog {

	@SuppressWarnings("unused")
	private DataBindingContext m_bindingContext;

	private EntrySearchCritieria criteria;

	private Label createdDateLabel;
	private Label hoursLabel;

	private DateTime createdOnWidget;
	private Combo createdDateOperatorCombo;
	private ComboViewer comboViewer;
	private Combo hoursOperatorCombo;
	private ComboViewer comboViewer_2;
	private Spinner spinner;

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public EntrySearchCriteriaDialog(Shell parentShell, EntrySearchCritieria criteria) {
		super(parentShell);
		if (criteria == null) {
			criteria = new EntrySearchCritieria();
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
		newShell.setText("Entry Search Criteria Settings");
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(3, false));
		new Label(container, SWT.NONE);new Label(container, SWT.NONE);new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);new Label(container, SWT.NONE);new Label(container, SWT.NONE);
		createdDateLabel = new Label(container, SWT.NONE);
		createdDateLabel.setText("Created On");

		comboViewer = new ComboViewer(container, SWT.NONE | SWT.READ_ONLY);
		createdDateOperatorCombo = comboViewer.getCombo();
		createdDateOperatorCombo.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, true, false, 1, 1));

		createdOnWidget = new DateTime(container, SWT.BORDER | SWT.DROP_DOWN);
		createdOnWidget.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 1, 1));

		hoursLabel = new Label(container, SWT.NONE);
		hoursLabel.setText("Total Hours");

		comboViewer_2 = new ComboViewer(container, SWT.NONE | SWT.READ_ONLY);
		hoursOperatorCombo = comboViewer_2.getCombo();
		hoursOperatorCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		spinner = new Spinner(container, SWT.BORDER);
		spinner.setDigits(2);
		spinner.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

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
	public EntrySearchCritieria getCriteria() {
		return criteria;
	}

	/**
	 * @param criteria
	 *            the criteria to set
	 */
	public void setCriteria(EntrySearchCritieria criteria) {
		this.criteria = criteria;
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer.setInput(SearchOperators.values());
		//
		IObservableValue comboViewer_6ObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer);
		comboViewer.setLabelProvider(new CustomLabelProvider());
		IObservableValue createdDateObserveValue = BeansObservables.observeValue(criteria, "entryDateOperator");
		bindingContext.bindValue(comboViewer_6ObserveSingleSelection, createdDateObserveValue, null, null);
		//
		comboViewer_2.setContentProvider(ArrayContentProvider.getInstance());
		comboViewer_2.setInput(SearchOperators.values());
		IObservableValue comboViewer_12ObserveSingleSelection = ViewersObservables.observeSingleSelection(comboViewer_2);
		comboViewer_2.setLabelProvider(new CustomLabelProvider());
		IObservableValue additionalWeightDeductionObserveValue = BeansObservables.observeValue(criteria, "hoursOperator");
		bindingContext.bindValue(comboViewer_12ObserveSingleSelection, additionalWeightDeductionObserveValue, null, null);
		//
		IObservableValue createdOnWidgetObserveSelectionObserveWidget = SWTObservables.observeSelection(createdOnWidget);
		IObservableValue criteriaCreatedDateObserveValue = BeansObservables.observeValue(criteria, "entryDate");
		bindingContext.bindValue(createdOnWidgetObserveSelectionObserveWidget, criteriaCreatedDateObserveValue, null, null);
		//
		IObservableValue hoursWidgetObserveSelectionObserveWidget = SWTObservables.observeSelection(spinner);
		IObservableValue criteriaHoursObserveValue = BeansObservables.observeValue(criteria, "hours");
		bindingContext.bindValue(hoursWidgetObserveSelectionObserveWidget, criteriaHoursObserveValue, null, null);
		return bindingContext;
	}
}
