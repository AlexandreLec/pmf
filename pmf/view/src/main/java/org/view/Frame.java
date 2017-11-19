package org.view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame
{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame(JPanel panel){
		
		this.setTitle("Allumer une LED");
        this.setSize(400, 100);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.setContentPane(panel);
	}
}
