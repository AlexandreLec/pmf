package org.contract;

public interface Imodel {
	
	public boolean connect(String port);
	
	public boolean disconnect();
	
	public int getTemperature();
	
	public void setTemperature();
	
	public void onLED();
	
	public void offLED();
	
	public String getLog();
	
	public boolean getConnected();
}
