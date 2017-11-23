package org.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.contract.Icontroller;
import org.contract.Imodel;

public class Frame extends JFrame implements Observer {
	private static final long serialVersionUID = 1L;
	
	  private JTabbedPane tabs;
	  
	  private Imodel model;

	public Frame(Imodel model, Icontroller controller){
		
		this.model = model;
		
	    this.setLocation(500,300);
	    this.setTitle("Pimp my Fridge");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(800, 600);
	    
        
        //Création de plusieurs Panneau

        Commandes commandes = new Commandes(model, controller);
        Stats stats = new Stats(model);
        Log log = new Log(model);

          

        //Création de notre conteneur d'onglets

        tabs = new JTabbedPane();


        tabs.add("Commandes", commandes);
        tabs.add("Statistiques", stats);
        tabs.add("Logs", log);


        //On passe ensuite les onglets au content pane

        this.getContentPane().add(tabs);
        this.setVisible(true);

        //pop-ups d'alertes
        
        this.model.observerAdd(this);
        
        
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("Boucle observable");
		
		
	}
}