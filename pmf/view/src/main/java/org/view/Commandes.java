package org.view;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.contract.Icontroller;
import org.contract.Imodel;

public class Commandes extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	
	private Imodel model;
	private Icontroller controller;
	
	private Button connect = new Button("Connect");
	private Button disconnect = new Button("Disconnect");
	private JComboBox<String> listePorts = new JComboBox<String>();
	private Button moins = new Button("-");
	private Button plus = new Button("+");
	private Label txt = new Label();
	private String valeur_temperature;
	private int number=0;
	private String T="0";
	private String Pt_rosee="0";
	private String H="0";
	private Label txt1 = new Label("Température actuelle : "+T);
	private Label txt2 = new Label("Point de rosée : "+Pt_rosee);
	private Label txt3 = new Label("Humidité : "+H);
	
	JPanel fr5 = new JPanel();

	public Commandes(Imodel model, Icontroller controller){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.model = model;
		this.controller = controller;	
		
		this.buildComSelector(this.model.getPortAvailable());
		
		this.add(listePorts);
		
        this.moins.addActionListener(this);
        this.moins.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Commandes.this.diminuer();}});
        this.plus.addActionListener(this);
        this.plus.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Commandes.this.augmenter();}});
        
        this.connect.addActionListener(this);
        this.disconnect.addActionListener(this);
        
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);

        afficher();
	}
	
	public void afficher(){
		this.removeAll();
        fr5.setBorder(new TitledBorder("Valeurs "));
        fr5.setLayout(new BoxLayout(fr5, BoxLayout.Y_AXIS));
        fr5.add(listePorts);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(connect);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(disconnect);
        fr5.add(Box.createVerticalStrut(30));
        fr5.add(plus);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(moins);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt1);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt2);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt3);
        this.add(fr5);
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
		fr5.removeAll();
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);
		fr5.revalidate();
		
		afficher();
	}
	
	public void Temperature(int x){
		T= Integer.toString(x);
		fr5.removeAll();
		txt1 = new Label("Température actuelle : "+T);
		fr5.revalidate();
		
		afficher();
	}
	
	public void Rosee(int x){
		Pt_rosee= Integer.toString(x);
		fr5.removeAll();
		txt2 = new Label("Point de rosée : "+Pt_rosee);
		fr5.revalidate();
		
		afficher();
	}
	
	public void Humidite(int x){
		H= Integer.toString(x);
		fr5.removeAll();
		txt3 = new Label("Humidité : "+H);
		fr5.revalidate();
		
		afficher();
	}
	
	public void augmenter(){
		number++;
		fr5.removeAll();
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);
		fr5.revalidate();
		
		afficher();
	}
	
	private void buildComSelector(List<String> portAvailable){
		
		for(int i = 0; i < portAvailable.size(); i++){
			this.listePorts.addItem(portAvailable.get(i));
		}
	}


	

	
}
