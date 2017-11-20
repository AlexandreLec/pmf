package org.main;

import org.controller.FridgeControl;
import org.model.Model;
import org.view.FridgeView;

/**
 * Hello world!
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
