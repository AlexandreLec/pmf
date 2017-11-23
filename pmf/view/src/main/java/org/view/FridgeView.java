package org.view;

import org.contract.Icontroller;
import org.contract.Imodel;

/**
 * Class that displays the Frame class
 * 
 * @author Lecomte Alexandre
 *
 */

public class FridgeView {
	
	private Imodel model;
	
	public FridgeView(Imodel model, Icontroller controller){
		this.model = model;
		Frame frame = new Frame(this.model, controller);
	}

}
