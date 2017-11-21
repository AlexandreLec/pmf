package org.view;

import java.awt.Button;
import java.awt.Color;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class Port extends JPanel{
	private static final long serialVersionUID = 1L;
	private JComboBox liste1;
	Button connect = new Button("Connect");
	Button disconnect = new Button("Disconnect");
	
	public Port(Color color){
		this.setBackground(new Color(77,116,185));
		this.setBounds(25, 50, 300, 50);
		
		Object[] com = new Object[]{"Com 1", "Com 2", "Com 3", "Com 4"};
		liste1 = new JComboBox(com);
		this.add(liste1);
		
		this.add(connect);
		this.add(disconnect);
	}

}
