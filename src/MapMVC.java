package extrex;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Alexander Cossins 2018
 */
class MapMVC extends JPanel {
    
    MapController mapCont;
    MapModel mapModel;

    /**
     * Constructor to integrate together all the components of the map MVC.
     */
    MapMVC() {
        
        mapModel = new MapModel();
        MapView mapView = new MapView(mapModel);
        mapCont = new MapController(mapView, mapModel);

        setBackground(Color.BLACK);
        
        // Set the map data within the JPanel.
        setPreferredSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        add(mapView);
        setVisible(true);
    }
    
    /**
     * Retrieve the model of the map for model swapping.
     * 
     * @return Model instance of the map MVC.
     */
    MapModel getModel(){
        return mapModel;
    }
    
    /**
     * Top level method to encapsulate the resuming/starting of the map mode.
     */
    void resumeMap() {
        mapCont.startMapRefresh();
    }
}
