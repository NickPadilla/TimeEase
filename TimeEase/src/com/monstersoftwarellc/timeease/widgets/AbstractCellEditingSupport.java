/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.IBeanValueProperty;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;

/**
 * @author nicholas
 *
 */
public abstract class AbstractCellEditingSupport extends ObservableValueEditingSupport {
	
	private IBeanValueProperty valueProperty;
	private DataBindingContext context;
	private UpdateValueStrategy updateStrategy;
	private UpdateValueStrategy updateStrategyModel;

	/**
	 * @param viewer
	 * @param dbc
	 */
	public AbstractCellEditingSupport(ColumnViewer viewer,
			DataBindingContext dbc, String pojoFieldName,
			UpdateValueStrategy updateStrategy) {
		super(viewer, dbc);

		this.context = dbc;
		this.valueProperty = BeanProperties.value(pojoFieldName);
		this.updateStrategy = updateStrategy;
	}
	
	/**
	 * @param viewer
	 * @param dbc
	 */
	public AbstractCellEditingSupport(ColumnViewer viewer,
			DataBindingContext dbc, String pojoFieldName,
			UpdateValueStrategy updateStrategy, UpdateValueStrategy updateStrategyModel) {
		super(viewer, dbc);

		this.context = dbc;
		this.valueProperty = BeanProperties.value(pojoFieldName);
		this.updateStrategy = updateStrategy;
		this.updateStrategyModel = updateStrategyModel;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport#
	 * createBinding
	 * (org.eclipse.core.databinding.observable.value.IObservableValue,
	 * org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@Override
	protected Binding createBinding(IObservableValue target,
			IObservableValue model) {
		return context.bindValue(target, model, updateStrategy, updateStrategyModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport#
	 * doCreateElementObservable(java.lang.Object,
	 * org.eclipse.jface.viewers.ViewerCell)
	 */
	@Override
	protected IObservableValue doCreateElementObservable(Object element,
			ViewerCell cell) {
		return valueProperty.observe(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport#canEdit
	 * (java.lang.Object)
	 */
	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
}
