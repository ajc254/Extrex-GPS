package extrex;

/**
 * TripComputer Controller
 * 
 * @author Louise Haggett 2018
 */
class TripComputerController {
    private TripComputerModel model;
    private TripComputerView view;

    /**
     * @param model The model to link to the controller.
     * @param view  The view to link to the controller.
     */
    public TripComputerController(TripComputerModel model, TripComputerView view) {
        this.model = model;
        this.view = view;
        startTripComputer();
    } 
    
    /**
     * Method to start the thread within TripComputerModel.
     */
    public void startTripComputer(){
        model.startTripComputer();
    }
}
