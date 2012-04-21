/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author nicholas
 * 
 */
public class TextCellEditingSupport extends AbstractCellEditingSupport {

	private TextCellEditor cellEditor;

	/**
	 * @param viewer
	 * @param dbc
	 * @param controlTargetFieldName
	 * @param pojoFieldName
	 * @param updateStrategy
	 */
	public TextCellEditingSupport(ColumnViewer viewer, DataBindingContext dbc,
			String pojoFieldName, UpdateValueStrategy updateStrategy) {
		super(viewer, dbc, pojoFieldName, updateStrategy);

		this.cellEditor = new TextCellEditor((Composite) viewer.getControl());
	}
	
	/**
	 * @param viewer
	 * @param dbc
	 * @param controlTargetFieldName
	 * @param pojoFieldName
	 * @param updateStrategy
	 * @param modelUpdateStrategy
	 */
	public TextCellEditingSupport(ColumnViewer viewer, DataBindingContext dbc,
			String pojoFieldName, UpdateValueStrategy updateStrategy, UpdateValueStrategy modelUpdateStrategy) {
		super(viewer, dbc, pojoFieldName, updateStrategy, modelUpdateStrategy);

		this.cellEditor = new TextCellEditor((Composite) viewer.getControl());
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
		return SWTObservables.observeDelayedValue(500, SWTObservables
				.observeText(this.cellEditor.getControl(), SWT.Modify));
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
}
