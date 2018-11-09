package extrex;

/**
 *
 * @author Jordan Tucker 2018
 */
class SatelliteController {

    private SatelliteModel model;
    private SatelliteView view;

    /**
     * @param model The model to link to the controller.
     * @param view The view to link to the controller.
     */
    public SatelliteController(SatelliteModel model, SatelliteView view) {
        this.model = model;
        this.view = view;
    }
    
    /**
     * Start the satellite
     */
    public void startSatellite(){
        model.startSatellite();
    }
}
