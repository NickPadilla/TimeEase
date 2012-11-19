/**
 * 
 */
package com.monstersoftwarellc.timeease.property.ui;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.monstersoftwarellc.timeease.property.IPropertyMetadata;
import com.monstersoftwarellc.timeease.property.IPropertyProxy;
import com.monstersoftwarellc.timeease.property.ISpelService;
import com.monstersoftwarellc.timeease.service.impl.ServiceLocator;
import com.monstersoftwarellc.timeease.widgets.CustomLabelProvider;

/**
 * Allows a dynamic ui to be built based upon the getters and setters of an object.
 * @author navid
 *
 */
public class PropertyComposite extends Composite {

	private IPropertyProxy propertyProxy;
	private DataBindingContext bindingContext;
	
	public PropertyComposite(Composite parent, int style, IPropertyProxy propertyProxy) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(2, false));
		
		this.propertyProxy = propertyProxy;
		
		bindingContext = new DataBindingContext();
		
		for(IPropertyMetadata meta : propertyProxy.getPropertyMetas()){
			if(!meta.isHidden()){
				Label temp = new Label(this, SWT.NONE);
				temp.setText(meta.getLabel());
				
				buildControlForMeta(meta);
			}
		}
	}


	private Object buildControlForMeta(IPropertyMetadata metadata){
		Object ret = null;
		Class<?> clazz = metadata.getType();
		
		if(metadata.hasChoice()){
			
			if(metadata.getListType() != null){
				ret = new ListViewer(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY);
				org.eclipse.swt.widgets.List list = ((ListViewer)ret).getList();
				list.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				
				applyCustomizations(ret,metadata);
				
				// add binding 
				((ListViewer)ret).setContentProvider(new ArrayContentProvider());
				((ListViewer)ret).setLabelProvider(new CustomLabelProvider());
				((ListViewer)ret).setInput(propertyProxy.getChoice(metadata));

				bindingContext.bindList(ViewersObservables.observeMultiSelection((ListViewer)ret), 
						PojoObservables.observeList(propertyProxy, metadata.getName(), metadata.getListType()), null, null);
				
			}else{
				ret = new ComboViewer(this, SWT.NONE | SWT.READ_ONLY);
				((ComboViewer)ret).getCombo().setLayoutData(new GridData(SWT.FILL,SWT.CENTER, true, false, 1, 1));
				
				applyCustomizations(ret,metadata);
				
				// add binding 	
				((ComboViewer)ret).setContentProvider(new ArrayContentProvider());
				((ComboViewer)ret).setLabelProvider(new CustomLabelProvider());
				((ComboViewer)ret).setInput(propertyProxy.getChoice(metadata));
				
//				((ComboViewer)ret).addSelectionChangedListener(listener)
				
				bindingContext.bindValue(ViewersObservables.observeSingleSelection((ComboViewer)ret),
										 BeansObservables.observeValue(propertyProxy, metadata.getName()));
			}
			
		}else if(Integer.class.isAssignableFrom(clazz) || int.class.isAssignableFrom(clazz) || short.class.isAssignableFrom(clazz)
					|| Long.class.isAssignableFrom(clazz) || long.class.isAssignableFrom(clazz)){
			
			ret = new Spinner(this, SWT.BORDER);
			GridData gd_spinner = new GridData(SWT.LEFT,SWT.CENTER, false, false, 1, 1);
			gd_spinner.widthHint = 140;
			((Spinner)ret).setLayoutData(gd_spinner);
			
			applyCustomizations(ret,metadata);
			
			// binding 
			bindingContext.bindValue(SWTObservables.observeDelayedValue(200, SWTObservables.observeSelection((Spinner)ret)),
									 BeansObservables.observeValue(propertyProxy, metadata.getName()));
			
		}else if(String.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz) ){
			ret = new Text(this, SWT.BORDER);
			((Text)ret).setLayoutData(new GridData(SWT.FILL, SWT.CENTER,true, false, 1, 1));
			
			applyCustomizations(ret,metadata);
			
			// add binding 
			bindingContext.bindValue(SWTObservables.observeDelayedValue(200,SWTObservables.observeText((Text)ret, SWT.Modify)),
					 BeansObservables.observeValue(propertyProxy, metadata.getName()));
			
		}else if(Boolean.class.isAssignableFrom(clazz) || boolean.class.isAssignableFrom(clazz)){
			ret = new Button(this, SWT.CHECK);
			((Button)ret).setLayoutData(new GridData(SWT.LEFT,SWT.CENTER, false, false, 1, 1));
			
			applyCustomizations(ret,metadata);
			
			// binding 
			bindingContext.bindValue(SWTObservables.observeSelection((Button)ret),
									 BeansObservables.observeValue(propertyProxy, metadata.getName()));
			
		}else {
			throw new IllegalArgumentException("Control cannot be created for property " + metadata.getName());
		}
		

		
		return ret;
	}
	
	private void applyCustomizations(Object control,IPropertyMetadata metadata){
		// apply desired customizations if any
		if(metadata.getUiCustomizations() != null){
			ISpelService spelService = ServiceLocator.locateCurrent(ISpelService.class);
			Map<String,Object> vars = new HashMap<String, Object>();
			vars.put("ui", control);
			for(String expression : metadata.getUiCustomizations()){
				spelService.getValue(expression, vars);
			}
		}
	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	
}
