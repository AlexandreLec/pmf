package org.model;

import java.util.ArrayList;
import java.util.Observable;

import org.contract.*;

public class Model extends Observable implements Imodel {
	
	private Connector connector;
	
	public double temperatureAmbiante;
	public double humiditeRelative;

	public Model(){
		
		this.connector = new Connector();
	}

	public int getTemperature() {
		this.connector.getArduinoInfos();
		return 0;
	}

	public void setTemperature(int temperature) {
		
		this.connector.writeData(temperature);
		
	}

	public void onLED() {
		
		this.connector.writeData(1);
	}

	public void offLED() {
		
		this.connector.writeData(0);
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

	public String getLog() {
		
		return this.connector.getTextLog(); 
	}
	
	public boolean getConnected(){
		return this.connector.getConnected();
	}

	public ArrayList<String> getPortAvailable() {
		
		return this.connector.searchForPort();
	}
	
	public double calculRosee(){
		
		double pointDeRosee;
		double K;
		
		K = (237 * temperatureAmbiante) / 17.7 + temperatureAmbiante + Math.log(humiditeRelative);
		pointDeRosee = (237.37 * K) / (17.7 - K);
		return pointDeRosee;
	}
	
}
	
