package org.contract;

import java.util.ArrayList;

public interface Imodel {
	
	public boolean connect(String port);
	
	public boolean disconnect();
	
	public int getTemperature();
	
	public void setTemperature(int temperature);
	
	public void onLED();
	
	public void offLED();
	
	public String getLog();
	
	public boolean getConnected();
	
	public ArrayList<String> getPortAvailable();
}
