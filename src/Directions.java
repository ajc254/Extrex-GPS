package extrex;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * Class to deal with route direction information fetching, using XML.
 * 
 *
 * @author Alexander Cossins 2018
 */
class Directions {

    private static Document document;

    /**
     * Fetch directions for a journey from the Google API in XML data format.
     *
     * @param origin The location coordinates to start the journey. E.G: "50, -3".
     * @param destination The destination coordinates.
     * @param region The region the journey will take place.
     * @param mode The type of HTTP protocol to be used.
     *
     * @return A byte array containing the response.
     * @throws IOException If the HTTP request fails, such as loss of internet.
     */
    private static byte[] readDirections(String origin,
            
        String destination,
        String region,
        String mode) throws IOException {
        try {
            final String encOrigin = URLEncoder.encode(origin, Const.URL_ENCODING);
            final String encDestination = URLEncoder.encode(destination, Const.URL_ENCODING);
            final String url // URL to send to the google API.
                    = ("https://maps.googleapis.com/maps/api/directions/xml"
                    + "?" + "origin" + "=" + encOrigin
                    + "&" + "destination" + "=" + encDestination
                    + "&" + "region" + "=" + region
                    + "&" + "mode" + "=" + mode
                    + "&" + "key=" + Const.DIRECTIONS_KEY);
            final byte[] body = {};
            final String[][] headers = {};
            byte[] response = HttpConnect.httpConnect(Const.HTTP_METHOD, url, headers, body);

            return response;

        } catch (UnsupportedEncodingException ex) {
            System.out.println("\nUnsupported URL encoding.\n");
            return null;
        }
    }

    /**
     * Obtains the most up-to-date directions.
     *
     * @param origin The current location coordinates.
     * @param destination The destination coordinates.
     * @throws IOException If the HTTP request fails, such as loss of internet.
     */
    static void updateDirections(String origin, String destination) throws IOException {
        
        // Obtain the direction byte array information.
        final byte[] ds = readDirections(origin, destination, Const.REGION, Const.MODE);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentbuilder = null;
        try {
            documentbuilder = documentBuilderFactory.newDocumentBuilder();

            // Parse the data in XML to create an interactive document with elements.
            document = documentbuilder.parse(new ByteArrayInputStream(ds));
        } catch (Exception e) {
            System.out.println("\nError with directions XML data format.\n");
        }
    }

    /**
     * Obtain the destination coordinates from the current XML data stored.
     *
     * @return The destination coordinates in the form: "latitude,longitude".
     */
    static String getDestCoordinates() {

        int numEndLocations = document.getElementsByTagName("end_location").getLength();

        // If there is no destination set.
        if (numEndLocations == 0) {
            return "";
        }

        // Find the relevant end_location element. (There are multiple end_location elements)
        Element endCoords = (Element) document.getElementsByTagName("end_location").item(numEndLocations - 1);
        String destLat = endCoords.getElementsByTagName("lat").item(0).getTextContent();
        String destLng = endCoords.getElementsByTagName("lng").item(0).getTextContent();

        return destLat + "," + destLng;
    }

    /**
     * Obtain the required information required to make a polyline in the map,
     * from the stored XML data.
     *
     * @return The encoded polyline data. (For use by Google Maps API)
     */
    static String getPolyline() {

        // If there is no destination set.
        if (document.getElementsByTagName("overview_polyline").getLength() == 0) {
            return "";
        }
        String polyline = document.getElementsByTagName("overview_polyline").item(0).getTextContent();

        return polyline.trim();
    }
}
