/**
 * 
 */
package com.monstersoftwarellc.timeease.test.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author nick
 *
 */
public class ComboBoxTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		shell.setLayout (new RowLayout ());
		Combo combo = new Combo (shell, SWT.NONE);
		combo.setItems (new String [] {"A-1", "B-1", "C-1"});
		Text text = new Text (shell, SWT.SINGLE | SWT.BORDER);
		text.setText ("some text");
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println (e.widget + " - Selected");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println (e.widget + " - Default Selection");
			}
		});
		text.addListener (SWT.DefaultSelection, new Listener () {
			public void handleEvent (Event e) {
				System.out.println (e.widget + " - Default Selection");
			}
		});
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

}
