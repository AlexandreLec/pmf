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
		System.out.print("hello");
    	this.model.connect(comId);
    }
    
    public void disconnect(){
    	this.model.disconnect();
    }
}
