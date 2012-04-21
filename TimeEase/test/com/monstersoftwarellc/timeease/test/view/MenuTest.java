/**
 * 
 */
package com.monstersoftwarellc.timeease.test.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author nick
 *
 */
public class MenuTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Display display = new Display ();
		Shell shell = new Shell (display);
		final Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI);
		final Menu menu = new Menu (shell, SWT.POP_UP);
		tree.setMenu (menu);
		for (int i=0; i<12; i++) {
			TreeItem item = new TreeItem (tree, SWT.NONE);
			item.setText ("Item " + i);
		}
		menu.addListener (SWT.Show, new Listener () {
			public void handleEvent (Event event) {
				MenuItem [] menuItems = menu.getItems ();
				for (int i=0; i<menuItems.length; i++) {
					menuItems [i].dispose ();
				}
				TreeItem [] treeItems = tree.getSelection ();
				for (int i=0; i<treeItems.length; i++) {
					MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
					menuItem.setText (treeItems [i].getText ());
				}
			}
		});
		Rectangle clientArea = shell.getClientArea ();
		tree.setBounds (clientArea.x, clientArea.y, 200, 200);
		shell.setSize (300, 300);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

}
