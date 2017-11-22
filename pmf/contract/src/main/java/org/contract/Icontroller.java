package org.contract;

public interface Icontroller {
	
	public void connect(String comId);
    
    public void disconnect();
    
    public void setTemperature(int temp);
}
