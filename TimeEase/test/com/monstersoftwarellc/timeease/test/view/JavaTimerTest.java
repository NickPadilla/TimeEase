/**
 * 
 */
package com.monstersoftwarellc.timeease.test.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * @author nicholas
 *
 */
public class JavaTimerTest extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Timer timer;

	int counter;

	JavaTimerTest(String title) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ActionListener a = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Counter = " + counter);

				if (++counter > 10) {
					timer.stop();
					System.exit(0);
				}
			}
		};

		timer = new Timer(300, a);
		timer.start();

		pack();
		setVisible(true);
	}
	
	 public static void main(String[] args) {
		    new JavaTimerTest("Timer Demo1");
		  }

}
