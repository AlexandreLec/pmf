package org.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.contract.*;

public class Model extends Observable implements Imodel {
	
	private Connector connector;

	public Model(){
		
		this.connector = new Connector();
	}

	public int getTemperature() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setTemperature() {
		// TODO Auto-generated method stub
		
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
		
		return this.connector.getConnected();
	}

	public boolean disconnect() {
		
		this.connector.disconnect();
		
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
	
}
