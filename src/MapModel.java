package extrex;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Alexander Cossins 2018
 */
class MapModel extends HomeModel {

    private final DrawMap draw;
    private int zoom;
    private boolean signal, initialMap, relocated;
    private String lat, tmpLat, lng, tmpLng;
    private double latValue, lngValue, prevLatUpdate, prevLngUpdate, orientation;
    MapRefresher mapRefresher;
    
    /**
     * Constructor creates reference to DrawMap, to retrieve and process a map.
     */
    MapModel() {
        
        draw = new DrawMap();
        zoom = 16;
        signal = true;
        initialMap = false;
        orientation = 0;
    }
    
    /**
     * Attempt to retrieve the current location coordinates.
     */
    void updateCoordinates() {
        
        // Store satellite response.
        tmpLat = Satellite.getSatellite().getLat();
        tmpLng = Satellite.getSatellite().getLng();
        
        try {
            latValue = Double.parseDouble(tmpLat);
            lngValue = Double.parseDouble(tmpLng);
            
            // The satellite was able to get valid coordinates:
            if (!signal) {
                setChanged();
                notifyObservers(MapStates.SIGNAL_REAQUIRED);
                
                // Update the map, removing the 'signal lost' message.
                relocated = true;
                signal = true;
            }
            lat = tmpLat;
            lng = tmpLng;
            
        } catch(NumberFormatException e) {
            
            // No signal was located:
            if (signal) {
                setChanged();
                notifyObservers(MapStates.SIGNAL_LOST);
                signal = false;
            }
        }
    }
    
    /**
     * Calculate the current travelling direction of the map user, using
     * previous known coordinates.
     */
    private void updateOrientation() {
        
        // Below I implement the Herversine formula to determine initial bearing.
        double dLon = (lngValue - prevLngUpdate);

        double y = Math.sin(dLon) * Math.cos(latValue);
        double x = Math.cos(prevLatUpdate) * Math.sin(latValue) - Math.sin(prevLatUpdate)
                * Math.cos(latValue) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;
        
        // Ensures the map rotates the correct way.
        brng = 360 - brng; 
        
        orientation = brng;
    }
    
    /**
     * Rotate the BufferedImage map respective to the current user's 
     * direction of travel.
     * 
     * @param mapImage The map image to be rotated.
     * @return The rotated map image, ready to be observed by the MapView.
     */
    BufferedImage rotateMap(BufferedImage mapImage) {
        
        int width = mapImage.getWidth();
        int height = mapImage.getHeight();
        
        // Create a new BufferedImage to store the rotated image in.
        BufferedImage rotated = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // AffineTransforms perform high quality, non-distorted rotation.
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(orientation), width/2, height/2);
        AffineTransformOp op = new AffineTransformOp(trans, AffineTransformOp.TYPE_BILINEAR);
        op.filter(mapImage, rotated);
        
        return rotated;
    }

    /**
     * Start a new thread to perform the background refreshing of the map.
     */
    void startMapRefresh() {
        
        mapRefresher = new MapRefresher();
        mapRefresher.start();
    }

    
    /**
     * Fetch a map centralised on the current location of the xTrex, convert to
     * a BufferedImage and notify MapView of the new map.
     * 
     * Synchronized to avoid race conditions between main and refreshing thread,
     * when updating a new map.
     */
    synchronized void generateMap() {
        
        BufferedImage mapImage = null;
        String polyline;
        String destCoordinates;
        String destination = WhereToView.destination;
        
        try {
            
            // If a destination is set.
            if (!destination.equals("")) {
                Directions.updateDirections(lat+","+lng, destination);
                polyline = Directions.getPolyline();
                destCoordinates = Directions.getDestCoordinates();
            }
            else {
                polyline = "";
                destCoordinates = "";
            }

            // Fetch the map from Google.
            draw.createMap(lat, 
                           lng, 
                           Integer.toString(zoom), 
                           Const.MAP_DIMENSION + "x" + Const.MAP_DIMENSION, 
                           destCoordinates, 
                           polyline);
        }
        
        // If there was an internet connection error, notify the MapView of this.
        catch (IOException ioe) {
            setChanged();
            notifyObservers(MapStates.CONNECTION_ISSUE);
            return;
        }
        
        // Attempt to read and proccess the produced map.
        try {
            mapImage = ImageIO.read(new File("assets/map.png"));
        } catch (IOException e) {
            System.out.println("\nError reading the map image file.\n");
        }
        assert mapImage != null : "Map unsuccessfully fetched, returning null";
        
        // Notify the MapView to elicit the map refresh using the rotated mapImage.
        setChanged();
        notifyObservers(rotateMap(mapImage));
    }
    
    /**
     * Method to forget all state, utilised when the power button is clicked.
     */
    private void resetState() {
        
        initialMap = false;
        signal = true;
        orientation = 0;
        zoom = 16;
        
        // Notify the MapView to clear the current map image state.
        setChanged();
        notifyObservers(MapStates.CLEAR_STATE);
    }
    
    
    /**
     * Select button has no functionality in map mode.
     */
    @Override
    public void selectButtonClicked() {}
    
    /**
     * Power button will stop the map mode execution, as well as the satellite
     * in use for map coordinates.
     */
    @Override
    public void powerButtonClicked() {
        
        /* 
         * Stop map refresh only if it is currently active. (power could be 
         * clicked outside of map mode)
        */
        if (mapRefresher != null) {
            mapRefresher.stopRefresh();
        }
        Satellite.stop();
        resetState();
    }
    
    /**
     * Zoom the map in by 1 point immediately and responsively.
     */
    @Override
    public void plusButtonClicked() {
        
        if(initialMap && zoom != 20) {
            zoom += 1;
            
            // Immediate response using current location.
            generateMap();
        }
    }
    
    /**
     * Zoom the map out by 1 point immediately and responsively.
     */
    @Override
    public void minusButtonClicked() {
        
        if(initialMap && zoom != 1) {
            zoom -= 1;
            
            // Immediate response using current location.
            generateMap();
        }
    }
    
    /**
     * Menu button will stop the map mode execution, as well as the satellite
     * in use for map coordinates.
     */
    @Override
    public void menuButtonClicked() {
        
        mapRefresher.stopRefresh();
        Satellite.stop();
    }
    
    /*
     * Encapsulated nested thread class, used in order to run the background 
     * fetching and refreshing of the map to the UI.
     */
    private class MapRefresher extends Thread {

        private boolean refresh;
        private boolean firstUpdate;
        
        private MapRefresher() {
            refresh = true;
            firstUpdate = false;
        }
        
        /**
         * Stop the thread, and as such, the map mode execution.
         */
        private void stopRefresh() {
            refresh = false;
        }
        
        /**
         * Perform fetching of the map, recording the last known location.
         */
        private void updateMap() {
            
            if (!signal) {
                return;
            }
            if (!initialMap) {
                initialMap = true;
            } else {
                updateOrientation();
            }
            generateMap();
            
            // Record last known location coordinate values.
            prevLatUpdate = latValue;
            prevLngUpdate = lngValue;
        }
        
        /**
         * Checks if the user has actually moved enough to justify a map update.
         * 
         * @return true if the user has moved enough to justify refresh, false if not.
         */
        private boolean notMoved() {
            
            return( initialMap && signal &&
                    Math.abs(latValue - prevLatUpdate) <= Const.MAP_UPDATE_TOLERANCE &&
                    Math.abs(lngValue - prevLngUpdate) <= Const.MAP_UPDATE_TOLERANCE);
        }

        /**
         * Runs the thread map refreshing continually until stopped.
         */
        public void run() {
            
            while (refresh) {
                
                // Retrieve current location coordinates.
                updateCoordinates();
                
                /* When re-entering map mode, ensure immediate update.
                 * (The destination could have changed, even if not moved).
                */
                if (!firstUpdate) {
                    updateMap();
                    firstUpdate = true;
                }
                
                // No refresh if not moved enough to justify a map update.
                if (notMoved()) {
                    
                    // Even if not moved, remove 'signal lost' message if needed.
                    if (relocated) {
                        updateMap();
                        relocated = false;
                    }
                    try {
                        Thread.sleep(Const.MAP_REFRESH_TIMER);
                    } catch (InterruptedException e) {
                        System.out.println("\nForced skip of map refresh wait.\n");
                    }
                    
                    // If not moved or relocated, nothing is done.
                    continue; 
                }
                updateMap();
                
                // Wait on the refresh timer before attempting to refresh again.
                try {
                    Thread.sleep(Const.MAP_REFRESH_TIMER);
                } catch (InterruptedException e) {
                    System.out.println("\nForced skip of map refresh wait.\n");
                }
            }
        }
    }
}
