package org.view;

import java.awt.Button;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import org.contract.Imodel;

public class Panel extends JPanel implements ActionListener {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Imodel model;
	
	private Button on = new Button("ON");
	
	private Button off = new Button("OFF");
	
	private Button connect = new Button("Connect");
	
	private Button disconnect = new Button("Disconnect");
	
	private TextArea log = new TextArea();

	public Panel(Imodel model){
		
		this.model = model;
		
        this.on.addActionListener(this);
        this.off.addActionListener(this);
        this.connect.addActionListener(this);
        this.disconnect.addActionListener(this);
        this.add(on);
        this.add(off);
        this.add(connect);
        this.add(disconnect);
        this.add(log);
	}

	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource().equals(this.on)){
			this.model.onLED();
			this.log.setText(this.model.getLog());
		}
		else if(arg0.getSource().equals(this.off)){
			this.model.offLED();
		}
		else if(arg0.getSource().equals(this.connect)){
			this.model.connect("COM11");
		}
		else if(arg0.getSource().equals(this.disconnect)){
			this.model.disconnect();
		}
		
	}
}
