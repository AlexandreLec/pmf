package org.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.contract.*;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

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
	}
	
	public void readDatas(String datas){
		
		System.out.println(datas);
		
		String[] data = datas.split("#");
		
		this.tempExt.add(new Double(data[0]));
		this.tempInt.add(new Double(data[1]));
		this.tempModule.add(new Double(data[2]));
		this.humidity.add(new Double(data[3]));
		
		System.out.println(tempExt.get(0));
		System.out.println(tempInt.get(0));
		System.out.println(tempModule.get(0));
		System.out.println(humidity.get(0));
		
	}

	public int getTemperature() {
		return 0;
	}

	public void setTemperature(int temperature) {
		
		this.connector.writeData(temperature);
		
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
	
	public double calculRosee(){
		
		double pointDeRosee;
		double K;
		
		K = (237.0 * tempInt.get(tempInt.size()-1)) / 17.7 + tempInt.get(tempInt.size()-1) + Math.log(humidity.get(tempInt.size()-1));
		pointDeRosee = (237.37 * K) / (17.7 - K);
		return pointDeRosee;
	}
	
}
	
