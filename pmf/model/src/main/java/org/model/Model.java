package org.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.contract.*;

public class Model extends Observable implements Imodel {
	
	/**
	 * Connector to the Arduino
	 */
	private Connector connector;
	
	/**
	 * List the available ports for connection
	 */
	private List<String> portsAvailable = new ArrayList<String>();
	
	/**
	 * Temperature in the fridge
	 */
	private List<Double> tempInt = new ArrayList<Double>();
	
	/**
	 * Temperature in the room
	 */
	private List<Double> tempExt = new ArrayList<Double>();
	
	/**
	 * Temperature of the Peltier Module
	 */
	private List<Double> tempModule = new ArrayList<Double>();
	
	/**
	 * Relative Humidity in the fridge 
	 */
	private List<Double> humidity = new ArrayList<Double>();

	public Model(){
		
		this.connector = new Connector(this);
		this.portsAvailable = this.connector.searchForPort();
		
		this.tempInt.add(0.0);
		this.tempExt.add(0.0);
		this.tempModule.add(0.0);
		this.humidity.add(0.0);
	}
	
	public void readDatas(String datas){
		
		String[] data = datas.split("#");
		
		this.tempModule.add(new Double(data[1]));
		this.tempExt.add(new Double(data[2]));
		this.tempInt.add(new Double(data[3]));
		this.humidity.add(new Double(data[4]));
		
		System.out.println(data[5]);
		
		this.notifyObserver();
	}

	public double getTemperature() {
		return this.tempModule.get(this.tempModule.size()-1);
	}

	public void setTemperature(int temperature) {
		
		this.connector.writeData(temperature);
		
	}
	
	public double getHumidityTx(){
		return this.humidity.get(this.humidity.size()-1);
	}

	public boolean connect(String port) {

		this.connector.searchPort();
        this.connector.connect(port);
        if (this.connector.getConnected() == true)
        {
            if (this.connector.initIOStream() == true)
            {
                this.connector.initListener();
            }
        }
        
        this.notifyObserver();
		
		return this.connector.getConnected();
	}

	public boolean disconnect() {
		
		this.connector.disconnect();
		
		this.notifyObserver();
		
		return this.connector.getConnected();
	}
	
    public void notifyObserver()
    {
            setChanged();
            notifyObservers();
    }
    
	public void observerAdd(Observer o) {
		this.addObserver(o);
	}
	
	public void observerDelete(Observer o){
		this.deleteObserver(o);
	}

	public String getLog() {
		
		return this.connector.getTextLog(); 
	}
	
	public boolean getConnected(){
		return this.connector.getConnected();
	}

	public List<String> getPortAvailable() {
		return this.portsAvailable;
	}
	
	public List<Double> getTempInt() {
		return this.tempInt;
	}
	
	public List<Double> getTempExt() {
		return this.tempExt;
	}
	
	public List<Double> getTempModule() {
		return this.tempModule;
	}
	
	public List<Double> getHumidity() {
		return this.humidity;
	}
	
	public double calculRosee(){
		
		double pointDeRosee;
		double K;
		
		K = ((17.27 * tempInt.get(tempInt.size()-1)) / (237.7 + tempInt.get(tempInt.size()-1)))+ Math.log((humidity.get(tempInt.size()-1)) / 100);
		pointDeRosee = (237.37 * K) / (17.27 - K);
		
		return pointDeRosee;
	}
	
}
	
