/**
 * 
 */
package com.monstersoftwarellc.timeease.test.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author nick
 *
 */
public class TimerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		final Label label = new Label(shell, SWT.CENTER);
		label.setText("00:00:00");
		label.setSize(100, 100);
		final long start = System.currentTimeMillis() - 10000; 
		Display.getDefault().asyncExec(new Runnable() { 
			int after60Sec = 0;
			int after60Min = 0;
			public void run() {  
				if (shell.isDisposed()) return;
				
				long elapsedTimeMillis = System.currentTimeMillis() - start;
				// Get elapsed time in seconds
				int elapsedTimeSec = (int)(elapsedTimeMillis/1000F);
				if(elapsedTimeSec >= 60){
					if(elapsedTimeSec % 60 == 0)++after60Sec;
					elapsedTimeSec = elapsedTimeSec - (60 * after60Sec);
				}
				
				// Get elapsed time in minutes
				int elapsedTimeMin = (int)(elapsedTimeMillis/(60*1000F));
				if(elapsedTimeMin >= 60){
					if(elapsedTimeMin % 60 == 0 && elapsedTimeSec == 0)++after60Min;
					elapsedTimeMin = elapsedTimeMin - (60 * after60Min);
				}
				
				// Get elapsed time in hours
				int elapsedTimeHour = (int)(elapsedTimeMillis/(60*60*1000F));
				if(elapsedTimeHour == 24){
					throw new IllegalStateException("Can only count up to a day, sorry!");
				}
				
				String time = (elapsedTimeHour < 10 ? "0" + elapsedTimeHour : elapsedTimeHour + "")
						+ ":" 
						+ (elapsedTimeMin < 10 ? "0" + elapsedTimeMin : elapsedTimeMin + "") 
						+ ":" 
						+ (elapsedTimeSec < 10 ? "0" + elapsedTimeSec : elapsedTimeSec + "");
				label.setText(time); 
				//System.out.println(time);
				display.timerExec (1000, this);
			}  
		}); 
		shell.setLayout (new RowLayout ());
		shell.setSize (200, 200);
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();	
	}

}
