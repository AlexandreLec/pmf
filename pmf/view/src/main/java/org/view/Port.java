package org.view;

import java.awt.Button;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class Port extends JPanel{
	private static final long serialVersionUID = 1L;
	private JComboBox listePorts;
	Button connect = new Button("Connect");
	Button disconnect = new Button("Disconnect");
	
	public Port(Color color){
		this.setBackground(new Color(77,116,185));
		this.setBounds(25, 50, 300, 50);
		
		listePorts = new JComboBox();
		//this.buildComSelector(coms);
		
		this.add(listePorts);
		
		this.add(connect);
		this.add(disconnect);
	}

	private void buildComSelector(ArrayList<String> portAvailable){
		
		for(int i = 0; i < portAvailable.size(); i++){
			this.listePorts.addItem(portAvailable.get(i));
		}
	}
}
