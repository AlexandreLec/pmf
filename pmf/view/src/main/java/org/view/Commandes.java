package org.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.contract.Icontroller;
import org.contract.Imodel;

public class Commandes extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	
	private Valeurs valeurs = new Valeurs();
	
	private Imodel model;
	private Icontroller controller;
	
	private Button connect = new Button("Connect");
	private Button disconnect = new Button("Disconnect");
	
	private JComboBox<String> listePorts = new JComboBox<String>();;
	
	private Button moins = new Button("-");
	private Button plus = new Button("+");
	
	private Label txt = new Label();
	private Font font = new Font("Arial",Font.BOLD,20);
	private String valeur_temperature;
	private int number=18;		

	public Commandes(Imodel model, Icontroller controller){
		
		this.model = model;
		this.controller = controller;	
		
		this.setLayout(null);
		
		this.buildComSelector(this.model.getPortAvailable());
		
		this.add(listePorts);
		this.add(valeurs);
		
		this.setBackground(new Color(77,116,185));
		this.setBounds(0,0,800,600);
		
        this.moins.addActionListener(this);
        this.moins.setBounds(50,200,50,50);
        this.moins.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Commandes.this.diminuer();}});
        
        this.plus.addActionListener(this);
        this.plus.setBounds(200,200,50,50);
        this.plus.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Commandes.this.augmenter();}});
        
        this.add(moins);
        this.add(plus);
        
        this.connect.addActionListener(this);
        this.disconnect.addActionListener(this);
        
        this.connect.setBounds(25, 50, 300, 50);
        this.disconnect.setBounds(25, 100, 300, 50);
        
        this.add(connect);
		this.add(disconnect);
        
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);
        
        this.add(txt); 
        txt.setBounds(140,200,100,50); 
        txt.setForeground(new Color(0,0,0));
        txt.setFont(font);
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(this.connect)){
			this.controller.connect(this.listePorts.getSelectedItem().toString());
		}
		else if(e.getSource().equals(this.disconnect)){
			this.controller.disconnect();
		}
	}
		
	public void diminuer(){
		number--;
		this.remove(txt);
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);
        this.add(txt);txt.setBounds(140,200,100,50);txt.setForeground(new Color(0,0,0));txt.setFont(font);
		this.revalidate();
	}
	
	public void augmenter(){
		number++;
		this.remove(txt);
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);
        this.add(txt);txt.setBounds(140,200,100,50);txt.setForeground(new Color(0,0,0));txt.setFont(font);
		this.revalidate();
	}
	
	private void buildComSelector(List<String> portAvailable){
		
		for(int i = 0; i < portAvailable.size(); i++){
			this.listePorts.addItem(portAvailable.get(i));
		}
	}


	

	
}
