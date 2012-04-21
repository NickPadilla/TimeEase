/**
 * 
 */
package com.monstersoftwarellc.timeease.test.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author nicholas
 *
 */
public class ResizeTextTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    Text text = new Text(shell, SWT.BORDER);
	    int columns = 10;
	    GC gc = new GC(text);
	    FontMetrics fm = gc.getFontMetrics();
	    int width = columns * fm.getAverageCharWidth();
	    int height = fm.getHeight();
	    gc.dispose();
	    text.setSize(text.computeSize(width, height));
	    shell.pack();
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	}

}
