package org.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;

import javax.swing.JPanel;

public class Valeurs extends JPanel{
	private static final long serialVersionUID = 1L;
	private Font font = new Font("Arial",Font.BOLD,17);
	
	private String T;
	private String Pt_rosee;
	private String H;
	
	Label txt1 = new Label("Température actuelle :");
	Label txt2 = new Label("Point de rosée :");
	Label txt3 = new Label("Humidité :");
	
	public Valeurs(){
		this.setBackground(new Color(77,116,185));
		this.setLayout(null);
		this.setBounds(400, 200, 300, 200);

		
        this.add(txt1);txt1.setBounds(0,0,250,50);txt1.setForeground(new Color(0,0,0));txt1.setFont(font);
        this.add(txt2);txt2.setBounds(0,50,250,50);txt2.setForeground(new Color(0,0,0));txt2.setFont(font);
        this.add(txt3);txt3.setBounds(0,100,250,50);txt3.setForeground(new Color(0,0,0));txt3.setFont(font);
        
	}
	
	public void Temperature(int x){
		T= Integer.toString(x);
		this.remove(txt1);
		txt1 = new Label("Température actuelle : "+T);
		this.add(txt1);txt1.setBounds(0,0,230,50);txt1.setForeground(new Color(0,0,0));txt1.setFont(font);
		this.revalidate();
	}
	
	public void Rosee(int x){
		Pt_rosee= Integer.toString(x);
		this.remove(txt1);
		txt1 = new Label("Température actuelle : "+Pt_rosee);
		this.add(txt1);txt1.setBounds(0,0,230,50);txt1.setForeground(new Color(0,0,0));txt1.setFont(font);
		this.revalidate();
	}
	
	public void Humidite(int x){
		H= Integer.toString(x);
		this.remove(txt1);
		txt1 = new Label("Température actuelle : "+H);
		this.add(txt1);txt1.setBounds(0,0,230,50);txt1.setForeground(new Color(0,0,0));txt1.setFont(font);
		this.revalidate();
	}

}
