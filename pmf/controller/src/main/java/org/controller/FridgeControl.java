package org.controller;

import org.contract.Icontroller;
import org.contract.Imodel;

public class FridgeControl implements Icontroller
{
	private Imodel model;
	
    public FridgeControl(Imodel model){
    	this.model = model;
    }
    
    public void connect(String comId){
    	this.model.connect(comId);
    }
    
    public void disconnect(){
    	this.model.disconnect();
    }
    
    public void setTemperature(int temp){
    	this.model.setTemperature(temp);
    }
}