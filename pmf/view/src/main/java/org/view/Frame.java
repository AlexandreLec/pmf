package org.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Frame extends JFrame{
	private static final long serialVersionUID = 1L;

	  private JTabbedPane onglet;

	public Frame(JPanel panel){
		
	    this.setLocation(500,300);
	    this.setTitle("Gérer vos conteneurs");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(800, 600);
        
        
        //Création de plusieurs Panneau

        Panel pan = new Panel(null, null);
        Stats stats = new Stats();

          

        //Création de notre conteneur d'onglets

        onglet = new JTabbedPane();


        onglet.add("Commandes", pan);
        onglet.add("Statistiques", stats);


        //On passe ensuite les onglets au content pane

        this.getContentPane().add(onglet);
        this.setVisible(true);

	}
}
