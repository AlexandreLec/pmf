package org.view;

import java.awt.Button;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.contract.Icontroller;
import org.contract.Imodel;

/**
 * 
 * @author  
 * 
 */
public class Commandes extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	
	private Imodel model;
	private Icontroller controller;
	
	private Button connect = new Button("Connect");
	private Button disconnect = new Button("Disconnect");
	private JComboBox<String> listePorts = new JComboBox<String>();
	private Button moins = new Button("-");
	private Button plus = new Button("+");
	private Button validate = new Button("Validate");
	private Label txt = new Label();
	private String valeur_temperature;
	private int number=17;
	private String T="0";
	private String Pt_rosee="0";
	private String H="0";
	private Label txt1 = new Label("Température actuelle : "+T);
	private Label txt2 = new Label("Point de rosée : "+Pt_rosee);
	private Label txt3 = new Label("Humidité : "+H);
	private Label alert = new Label("/!\\ Attention, risque de condensation");
	
	JPanel fr5 = new JPanel();

	/**
	 * 
	 * @param model
	 * @param controller
	 */
	public Commandes(Imodel model, Icontroller controller){
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.model = model;
		this.controller = controller;	
		
		this.model.observerAdd(this);
		
		this.buildComSelector(this.model.getPortAvailable());
		
		this.add(listePorts);
		
		this.alert.setForeground(Color.RED);
		this.alert.setVisible(false);
		
        this.moins.addActionListener(this);
        this.moins.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Commandes.this.diminuer();}});
        this.plus.addActionListener(this);
        this.plus.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Commandes.this.augmenter();}});
        this.validate.addActionListener(this);
        this.validate.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){Commandes.this.sendConsigne();}});
        
        this.connect.addActionListener(this);
        this.disconnect.addActionListener(this);
        
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);
        txt.setAlignment(Label.CENTER);

        afficher();
	}
	
	public void afficher(){
		this.removeAll();
        fr5.setBorder(new TitledBorder("Commandes "));
        fr5.setLayout(new BoxLayout(fr5, BoxLayout.Y_AXIS));
        fr5.add(listePorts);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(connect);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(disconnect);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(plus);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(moins);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(validate);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt1);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt2);
        fr5.add(Box.createVerticalStrut(10));
        fr5.add(txt3);
        this.add(fr5);
        this.add(this.alert);

		/**valeurs.setBorder(new TitledBorder("Valeurs "));
	    valeurs.setLayout(new BoxLayout(valeurs, BoxLayout.Y_AXIS));
	    valeurs.add(Box.createVerticalStrut(10));
	    valeurs.add(txt1);
	    valeurs.add(Box.createVerticalStrut(10));
	    valeurs.add(txt2);
	    valeurs.add(Box.createVerticalStrut(10));
	    valeurs.add(txt3);
	    this.add(valeurs);*/
	}

	public void update(Observable o, Object arg) {
		this.Temperature();
		this.Humidite();
		this.Rosee();
    	
    	
    	System.out.println(String.valueOf(this.model.openDoor()));
		this.checkAlert();
		
		System.out.println("update");
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(this.connect)){
			this.controller.connect(this.listePorts.getSelectedItem().toString());
		}
		else if(e.getSource().equals(this.disconnect)){
			this.controller.disconnect();
		}
	}
	
	private void checkAlert(){
		
		if(this.model.openDoor()){
        	
        	JOptionPane.showMessageDialog(this, "La porte est ouverte");
        }
        
        if(this.model.condensation()){
        	this.alert.setVisible(true);
        }
        else {
        	this.alert.setVisible(false);
        }
	}

	public void sendConsigne(){
		this.controller.setTemperature(Integer.parseInt(this.txt.getText()));
	}
		
	public void diminuer(){
		number--;
		fr5.removeAll();
        valeur_temperature = Integer.toString(number);
        txt = new Label(valeur_temperature);
		
		afficher();
	}
	
	public void Temperature(){
		T= Double.toString(this.model.getTemperature());
		fr5.removeAll();
		txt1 = new Label("Température actuelle : "+T);
		fr5.revalidate();
		
		afficher();
	}
	
	public void Rosee(){
		Pt_rosee= Double.toString(this.model.getRosee());
		fr5.removeAll();
		txt2 = new Label("Point de rosée : "+Pt_rosee);
		fr5.revalidate();
		
		afficher();
	}
	
	public void Humidite(){
		H= Double.toString(this.model.getHumidityTx());
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
