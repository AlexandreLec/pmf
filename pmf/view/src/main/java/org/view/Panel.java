package org.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import org.contract.Icontroller;
import org.contract.Imodel;

public class Panel extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	
	private Valeurs valeurs = new Valeurs();
	
	private Port portSelector;
	
	private Imodel model;
	private Icontroller controller;
	
	private Button moins = new Button("-");
	private Button plus = new Button("+");
	
	private Label txt = new Label();
	private Font font = new Font("Arial",Font.BOLD,20);
	private String valeur_temperature;
	private int number=18;		

	public Panel(Imodel model, Icontroller controller){
		
		this.model = model;
		this.controller = controller;	
		
		this.setLayout(null);
		
		this.portSelector = new Port(Color.blue);
		
		this.add(portSelector);
		this.add(valeurs);
		
		this.setBackground(new Color(77,116,185));
		this.setBounds(0,0,800,600);
		
        this.moins.addActionListener(this);
        this.moins.setBounds(50,200,50,50);
        this.moins.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Panel.this.diminuer();}});
        
        this.plus.addActionListener(this);
        this.plus.setBounds(200,200,50,50);
        this.plus.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Panel.this.augmenter();}});
        
        this.add(moins);
        this.add(plus);
        
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
		// TODO Auto-generated method stub
		
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


	

	
}
