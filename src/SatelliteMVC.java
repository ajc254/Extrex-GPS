package extrex;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * 
 * @author Jordan Tucker 2018
 */
class SatelliteMVC extends JPanel {
    
    SatelliteController controller;
    SatelliteModel model;

    public SatelliteMVC() {
        
        // Create the mvc.
        model = new SatelliteModel();
        SatelliteView view = new SatelliteView(model);
        controller = new SatelliteController(model, view);

        setBackground(Color.BLACK);
        
        // Set the properties of the JPanel.
        setPreferredSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        add(view);
        setVisible(true);
    }
    
    /*
     * Get the satellite model of this mvc
     *
     * @returns A satellite model
    */
    public SatelliteModel getModel(){
        return model;
    }
    
    /*
     * Start the satellite
    */
    public void resumeSatellite(){
        controller.startSatellite();
    }
}
