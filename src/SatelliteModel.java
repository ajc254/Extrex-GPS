package extrex;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Jordan Tucker 2018
 */
class SatelliteModel extends HomeModel {
    
    String[][] latlng; 
    private Thread fetchDataThread;
    private boolean isRunning;

    public SatelliteModel() {
        latlng = new String[2][2];
        setChanged();
        notifyObservers();
    }
    
   /**
     * Start reading data from the satellite object.
     */
    public void startSatellite() {
        
        runSatellite();
        isRunning = true;
        
        // Run the fetching in the background so that the GUI can be updated.
        fetchDataThread = new Thread() {
            @Override
            public void run() {
                
                while (isRunning) {
                    try{
                        latlng[0] = Satellite.getSatellite().getLatWithDirection();
                        latlng[1] = Satellite.getSatellite().getLngWithDirection();
                        setChanged(); notifyObservers(latlng);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }catch(Exception e){
                        System.out.println("Satellite interrupted \n Restart device to start again");
                    }
                }
            }
        };
        fetchDataThread.start();
    }
    
    /*
     * Stop running the satellite
    */
    public void stopSatellite(){
        isRunning = false;
        Satellite.stop();
    }
    
    @Override
    public void menuButtonClicked(){
        stopSatellite();
    }
    
    @Override
    public void powerButtonClicked(){
        stopSatellite();
        Satellite.reset();
    }

    /*
     * Disable plus, minus and select buttons for this package.
    */
    @Override
    public void plusButtonClicked(){}
    @Override
    public void minusButtonClicked(){}
    @Override
    public void selectButtonClicked(){}
    
    public String[][] getLatLng(){
        return latlng;
    }
}
