package org.view;

import org.contract.Icontroller;
import org.contract.Imodel;

public class FridgeView {
	
	private Imodel model;
	
	public FridgeView(Imodel model, Icontroller controller){
		this.model = model;
		Frame frame = new Frame(new Panel(this.model, controller));
	}

}
