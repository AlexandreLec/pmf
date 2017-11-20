package org.view;

import java.awt.Button;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.contract.Icontroller;
import org.contract.Imodel;

public class Panel extends JPanel implements ActionListener, Observer {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Imodel model;
	
	private Icontroller controller;
	
	private Button connect = new Button("Connect");
	
	private Button disconnect = new Button("Disconnect");
	
	private Label label = new Label();
	
	private TextArea log = new TextArea();
	
	private List comSelector = new List();

	public Panel(Imodel model, Icontroller controller){
		
		this.model = model;
		this.controller = controller;
		
        this.connect.addActionListener(this);
        this.disconnect.addActionListener(this);
        
        this.label.setText(String.valueOf(this.model.getConnected()));
        
        this.buildComSelector(this.model.getPortAvailable());
        
        this.add(connect);
        this.add(disconnect);
        this.add(label);
        this.add(comSelector);
	}

	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource().equals(this.connect)){
			this.controller.connect("COM11");
		}
		else if(arg0.getSource().equals(this.disconnect)){
			this.controller.disconnect();
		}
		
	}

	public void update(Observable o, Object arg) {
		
		this.repaint();
		
	}
	
	private void buildComSelector(ArrayList<String> portAvailable){
		
		for(int i = 0; i < portAvailable.size(); i++){
			this.comSelector.add(portAvailable.get(i));
		}
		
	}
}
