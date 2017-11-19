package org.view;

import org.contract.Imodel;

public class FridgeView {
	
	private Imodel model;
	
	public FridgeView(Imodel model){
		this.model = model;
		Frame frame = new Frame(new Panel(this.model));
	}

}
