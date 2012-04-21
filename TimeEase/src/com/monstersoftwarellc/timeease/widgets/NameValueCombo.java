/**
 * 
 */
package com.monstersoftwarellc.timeease.widgets;

import java.util.List;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * @author nicholas
 *
 */
public class NameValueCombo {
	
	private Combo combo;
	
	private List<String[][]> nameValuePairs;

	public NameValueCombo(Composite parent, int style) {
		combo = new Combo(parent, style);
	}
	
	public void setComboTooltip(String toolTip){
		this.combo.setToolTipText(toolTip);
	}
	
	public void setComboLayoutData(GridData data){
		combo.setLayoutData(data);
	}

	/**
	 * @return the nameValuePairs
	 */
	public List<String[][]> getNameValuePairs() {
		return nameValuePairs;
	}
	
	public void setNameValueItems(List<String[][]> valuePairs, long defaultValue){
		this.nameValuePairs = valuePairs;
		// always reset
		combo.setItems(new String[0]);
		String defaultSelection = "Please Select..";
		combo.add(defaultSelection, 0);
		int index = 1;
		for (String[][] array : valuePairs) {
			combo.add(array[0][0], index);
			
			// set deafult, if we find one
			if(defaultValue == Long.parseLong(array[0][1])){
				defaultSelection = array[0][0];
			}
			index++;
		}
		combo.setText(defaultSelection);
	}
	
	public long getCurrentlySelected(){
		long value = 0;
		for (String[][] array : nameValuePairs) {
			if(combo.getText() != null && combo.getText().equals(array[0][0])){
				value = Long.parseLong(array[0][1]);
			}
		}
		return value;
	}
	
	public long getValue(String name){
		long value = 0;
		for (String[][] array : nameValuePairs) {
			if(name != null && name.equals("Combo {"+array[0][0]+"}")){
				value = Long.parseLong(array[0][1]);
			}
		}
		return value;
	}

	public void addSelectionListener(SelectionListener selectionListener) {
		combo.addSelectionListener(selectionListener);
	}

}
