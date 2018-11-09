package extrex;

/**
 * HomeModel
 * 
 * @author Romemary Naish 2018
 * @author Louise Haggett 2018 (added enums)
 */
class HomeModel extends Model {

    private static Thread satelliteThread;
    
     /**
      * Constructor for HomeModel.
      */
    public HomeModel( ) {
        setChanged();
        notifyObservers();
    }
    
    /**
     * @author Jordan Tucker 2018
     * @author Alex Cossins 2018
    */
    public void runSatellite(){
        
        satelliteThread = new Thread(Satellite.getSatellite());
        satelliteThread.start();
    }
   
    /**
     * @author Rosemary Naish 2018
     * A method to deal with the plus button clicked and will notify the update method with an appropriate string.
     */
    public void plusButtonClicked(){
        
        setChanged();
        notifyObservers(ButtonStates.PLUS);
    }
    
    /**
     * A method to deal with the minus button clicked and will notify the update method with an appropriate string.
     */
    public void minusButtonClicked(){
        
       setChanged();
       notifyObservers(ButtonStates.MINUS);
   }
    /**
     * A method to deal with the power button clicked and will notify the update method with an appropriate string.
     */
    public void powerButtonClicked(){
        
        WhereToView.reset();
        setChanged();
        notifyObservers(ButtonStates.POWER);
        
    }
    /**
     * A method to deal with the menu button clicked and will notify the update method with an appropriate string.
     */
    public void menuButtonClicked(){
        
        setChanged();
        notifyObservers(ButtonStates.MENU);
    }
    /**
     * A method to deal with the select button clicked and will notify the update method with an appropriate string.
     */
    public void selectButtonClicked(){
        
        setChanged();
        notifyObservers(ButtonStates.SELECT);
    }
    
}
