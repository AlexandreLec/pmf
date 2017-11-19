package org.main;

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
        
        FridgeView view = new FridgeView(model);
    }
}
