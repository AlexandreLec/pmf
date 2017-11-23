package org.main;

import org.controller.FridgeControl;
import org.model.Model;
import org.view.FridgeView;

/**
 * Main class
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Model model = new Model();
        
        FridgeControl controller = new FridgeControl(model);
        
        FridgeView view = new FridgeView(model, controller);
    	
    }
}
