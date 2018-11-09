package extrex;

/**
 *
 * @author Alexander Cossins 2018
 */
class MapController {
    final private MapView mapView;
    final private MapModel mapModel;
    
    /**
     * Constructor stores reference to both MapModel and MapView, to allow
     * mediation between the two.
     * 
     * @param mapView
     * @param mapModel 
     */
    MapController(MapView mapView, MapModel mapModel ) {
        this.mapModel = mapModel;
        this.mapView = mapView;
    }
    
    /**
     * Start the Map refreshing.
     */
    void startMapRefresh() {
        mapModel.startMapRefresh();
    }
}
