package extrex;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * DrawMap utilises Google Mapping Services.
 *
 * @author Alexander Cossins 2018
 */
class DrawMap {

    /**
     * Connects to Google and requests for map production.
     * 
     * @param latitude The latitude to focus the map.
     * @param longtitde The longitude to focus the map.
     * @param zoom The magnification of the map.
     * @param size The resolution of the image. (E.G. "100x100")
     * @param destination The destination coordinates of a route.
     * @param polyline The encoded polyline data to use.
     * 
     * NOTE: 'destination' and 'polyline' will be empty strings if there is no 
     *        destination set.
     * 
     * @return Map image data.
     */
    void createMap(String latitude,
                            String longitude,
                            String zoom,
                            String size,
                            String destination,
                            String polyline) throws IOException {
        
        final String url
                = ("https://maps.googleapis.com/maps/api/staticmap"
                + "?" + "center" + "=" + latitude + "," + longitude
                + "&" + "zoom" + "=" + zoom
                + "&" + "size" + "=" + size
                + "&" + "markers=color:blue%7Clabel:F%7C" + destination
                + "&" + "path=weight:5|color:red|enc:" + polyline
                + "&" + "key" + "=" + Const.MAP_KEY);
        final byte[] body = {};
        final String[][] headers = {};
        byte[] response = HttpConnect.httpConnect(Const.HTTP_METHOD, url, headers, body);
        
        writeData(Const.MAP_LOCATION, response);
    }

    /**
     * Takes image data and stores the image file at a specified location path.
     * 
     * @param file Path to save the image file.
     * @param data Image data.
     */
    private void writeData(String file, byte[] data) {
        
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(data, 0, data.length);
            os.close();
        } catch (IOException ex) {
            System.out.println("\nError writing the map image to given location.\n");
        }
    }
}
