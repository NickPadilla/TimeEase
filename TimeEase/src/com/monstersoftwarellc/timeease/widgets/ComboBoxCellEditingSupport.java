/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author nicholas
 * 
 */
public class ComboBoxCellEditingSupport extends AbstractCellEditingSupport {

	private CustomComboBoxViewerCellEditor cellEditor;

	/**
	 * @param viewer
	 * @param dbc
	 */
	public ComboBoxCellEditingSupport(ColumnViewer viewer,
			DataBindingContext dbc, Object input, String pojoFieldName,
			UpdateValueStrategy updateStrategy) {
		super(viewer, dbc, pojoFieldName, updateStrategy);

		this.cellEditor = new CustomComboBoxViewerCellEditor(
				(Composite) viewer.getControl(), SWT.NONE | SWT.READ_ONLY);
		this.cellEditor.setInput(input);
	}

	/**
	 * @param viewer
	 * @param dbc
	 */
	public ComboBoxCellEditingSupport(ColumnViewer viewer,
			DataBindingContext dbc, Object input, String pojoFieldName,
			UpdateValueStrategy updateStrategy,
			UpdateValueStrategy updateStrategyModel) {
		super(viewer, dbc, pojoFieldName, updateStrategy, updateStrategyModel);

		this.cellEditor = new CustomComboBoxViewerCellEditor(
				(Composite) viewer.getControl(), SWT.READ_ONLY);
		this.cellEditor.setInput(input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport#
	 * doCreateCellEditorObservable(org.eclipse.jface.viewers.CellEditor)
	 */
	@Override
	protected IObservableValue doCreateCellEditorObservable(
			CellEditor cellEditor) {
		return ViewersObservables.observeSingleSelection(this.cellEditor
				.getViewer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.EditingSupport#getCellEditor(java.lang.Object)
	 */
	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	/**
	 * Convienance method to allow for listeners on the combo box.
	 * 
	 * @param listener
	 * @return
	 */
	public ComboBoxCellEditingSupport addSelectionListener(
			ICellEditorListener listener) {
		this.cellEditor.addListener(listener);
		return this;
	}

	/**
	 * Convienance method to allow for listeners on the combo box.
	 * 
	 * @param listener
	 * @return
	 */
	public ComboBoxCellEditingSupport addPropertyChangeListener(
			IPropertyChangeListener listener) {
		this.cellEditor.addPropertyChangeListener(listener);
		return this;
	}
}
