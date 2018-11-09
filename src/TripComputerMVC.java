package extrex;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * TripComputer MVC.
 * 
 * @author Louise Haggett 2018
 */
class TripComputerMVC extends JPanel {
    
    TripComputerModel tripComputerModel;
    
    public TripComputerMVC() {
        
        // Create the MVC.
        tripComputerModel = new TripComputerModel();
        TripComputerView view = new TripComputerView(tripComputerModel);
        new TripComputerController(tripComputerModel, view);

        setBackground(Color.BLACK);
        
        // Set the properties of the JPanel.
        setPreferredSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        add(view);
        setVisible(true); 
    }
    
    /**
     * Retrieve the model of the Trip Computer for model swapping.
     * 
     * @return Model instance of the Trip Computer MVC.
     */
    public TripComputerModel getModel(){
        return tripComputerModel;
    }
}
