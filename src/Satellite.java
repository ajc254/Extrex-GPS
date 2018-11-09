package extrex;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Jordan Tucker 2018
 */
class Satellite implements Runnable {

    static Double distanceTravelled;
    private static int falseCount;
    
    private String prevLat;
    private String prevLng;
    private static String lat;
    private static String lng;
    private static String latDirection;
    private static String lngDirection;
    
    private static boolean initial;
    private static boolean running;
    private static boolean isMoving;
    private static boolean modemAvailable;
    private static boolean hasSignal;
    
    private static final Satellite SATELLITE = new Satellite();

    private Satellite() {
        reset();
    }
    
    public static Satellite getSatellite() {
        return SATELLITE;
    }

    /**
     * A method to get the latitude of the user.
     *
     * @return The latitude of the user.
     */
    public static String getLat() {
        
        switch (latDirection) {
            case "N":
                return lat;
            case "S":
                return "-" + lat;
            default:
                return "";
        }
    }
    
    /**
     * A method to get the latitude of the user along with the direction (N, S)
     *
     * @return The latitude and latitude direction of the user.
     */
    public String[] getLatWithDirection(){
        String[] l = {lat, latDirection};
        return l;
    }
    
    /**
     * A method to get the longitude of the user along with the direction (W, E)
     *
     * @return The latitude and longitude direction of the user.
     */
    public String[] getLngWithDirection(){
        String[] l = {lng, lngDirection};
        return l;
    }

    /**
     * A method to get the longitude of the user.
     *
     * @return The longitude of the user.
     */
    public static String getLng() {
        
        switch (lngDirection) {
            case "E":
                return lng;
            case "W":
                return "-" + lng;
            default:
                return "";
        }
    }

    /**
     * Start reading GPS data.
     *
     * @param fileName The name of the GPS driver file.
     */
    private void reader(String fileName) {
        
        try {
            FileInputStream in = new FileInputStream(new File(fileName));
            byte[] buffer = new byte[Const.BUFF_SIZE];
            String s;
            int n;
            
            modemAvailable = true;

            while ((n = in.read(buffer)) > -1) {
                if (running) {
                    s = new String(buffer, 0, n);
                    parseLL(s);
                } else {
                    break;
                }
            }
            
            //If there is no more data to be read, it means the modem has been disconnected
            modemAvailable = false;
            
        } catch (Exception e) {
            
            // The modem cannot be found.
            System.out.println("Error " + e);
            modemAvailable = false;
            hasSignal = false;
        }
    }

    /**
     * Parse the string made by the GPS dongle into useful data (Not yet
     * implemented).
     * 
     * @param s The string produced by the GPS dongle.
     */
    private void parseLL(String s) {
        
        //Try to parse a signal, if there isn't one, say there is no signal
        try{
            List<String> data = Arrays.asList(s.split(","));
            
            if(!data.get(0).equals("$GPGLL")){
                return;
            }
        
            /* 
             * Extract the data from the list into
             * new variable so that the originals can be rolled back to if signal is lost
             */
            String nLat = data.get(1);
            String nLatDirection = data.get(2);
            String nLng = data.get(3);
            String nLngDirection = data.get(4);
            
            hasSignal = true;
            
            int deglat = (int) Float.parseFloat(nLat)/100;
            int deglng = (int) Float.parseFloat(nLng)/100;
            
            float flat = ((Float.parseFloat(nLat) - (deglat*100)) / 60);
            float flng = ((Float.parseFloat(nLng) - (deglng*100)) / 60);
            
            nLat = Float.toString(deglat + flat);
            nLng = Float.toString(deglng + flng);
           
            lat = nLat;
            lng = nLng;
            latDirection = nLatDirection;
            lngDirection = nLngDirection;
           
           
            if(!initial){
                initial = true;
                prevLat = lat;
                prevLng = lng;
            }else{
                updateInformation();
                if(isMoving){
                    prevLat = lat;
                    prevLng = lng;
                }
            }
                
        }catch(Exception e){
            hasSignal = false;
        }
    }
    
    /*
     * Calculate if the user has moved or not and if so, how far have they travelled
    */
    public void updateInformation(){
        
        if(prevLat != null && hasSignal){
            isMoving = (Math.abs(Double.parseDouble(lat) - Double.parseDouble(prevLat)) >= Const.MAP_UPDATE_TOLERANCE) || (Math.abs(Double.parseDouble(lng) - Double.parseDouble(prevLng)) >= Const.MAP_UPDATE_TOLERANCE);
            // If the user is moving or allow FALSE_COUNT seconds to stop.
            if(isMoving || falseCount < Const.FALSE_COUNT){
                if(isMoving){
                    falseCount = 0;
                }else{
                    falseCount++;
                }
                getDistance(Double.parseDouble(lat), Double.parseDouble(lng), Double.parseDouble(prevLat), Double.parseDouble(prevLng));        
            }
        }  
    }
    
    /**
     * Calculate the distance in KM between two points.
     *
     * @returns A distance between two points.
     */
    private void getDistance(double lat1, double lng1, double lat2, double lng2) {
        
        //difference of latitudes
        double dLat = Math.toRadians(lat2-lat1); 
        
        //difference of longitudes
        double dLon = Math.toRadians(lng2-lng1); 
        
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = Const.EARTH_RADIUS * c; 
        distanceTravelled += d;
    }
    
    /*
     * True if the user is moving.
     * @Returns true if the user is moving
    */
    public static boolean isMoving(){
        return isMoving;
    }
    
    /*
     * True if the modem is plugged in
     * @Returns true if the modem is plugged in 
    */
    public static boolean hasModem(){
        return modemAvailable;
    }
    
    /*
     * True if there is a signal
     * @Returns true if the modem has a signal 
    */
    public static boolean hasSignal(){
        return hasSignal;
    }
    
    /*
     * Stop the GPS from searching for a satellite.
    */
    public static void stop() {
        running = false;
    }
    
    /*
     * Reset the satellite state
    */
    public static void reset(){
        
        running = false;
        distanceTravelled = 0d;
        isMoving = false;
        modemAvailable = true;
        hasSignal = false;
        lat = "";
        lng = "";
        latDirection = "";
        lngDirection = "";
        falseCount = Const.FALSE_COUNT;
    }
    
    /*
     * Start the GPS to search for a satellite.
    */
    public void start() {
        running = true;
        reader(Const.FILE_NAME);
    }
    
    @Override
    public void run() {
        start();
    }
}
