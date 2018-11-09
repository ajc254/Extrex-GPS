package extrex;

/**
 * File to keep track of project global constants
 * 
 * @author Alexander Cossins 2018
 * @author Louise Haggett 2018
 */
final class Const {
    
    public Const() {
        //Guard Instantiation
    }

    final static int FRAME_WIDTH = 540;
    final static int FRAME_HEIGHT = 985;
    final static int PANEL_WIDTH = 300;
    final static int PANEL_HEIGHT = 366;
    
    //WhereTo Specific Constants.
    final static int TEXT_X = 10;
    final static int TEXT_Y = 10;
    final static int KEYBOARD_X = 10;
    final static int KEYBOARDY = 75;
    final static int KEYBOARD_WIDTH = 270;
    final static int KEYBOARD_HEIGHT = 290;
    final static int PANEL_1_MAX_VAL = 27;
    final static int PANEL_2_MAX_VAL = 11;
    final static int KEY_ROWS = 7;
    final static int KEY_COLUMNS = 4;
    final static int TEXT_HEIGHT = 50;
    final static int INNER_PANEL_HEIGHT = 70;
    final static int KEY_WIDTH = 87;
    final static int KEY_HEIGHT = 56;
    final static int FALSE_COUNT = 3;
    
    // Map Specific Constants.
    final static int MAP_DIMENSION = 500;
    final static int MAP_DOT_DIAMETER = 15;
    final static int MAP_DOT_LOCATION = (MAP_DIMENSION/2)-MAP_DOT_DIAMETER/2;
    final static int SIGNAL_LOST_WIDTH = MAP_DIMENSION/3+30;
    final static int SIGNAL_LOST_HEIGHT = MAP_DIMENSION/5;
    final static int CONNECTION_LOST_WIDTH =  MAP_DIMENSION/3+25; 
    final static int CONNECTION_LOST_HEIGHT =  MAP_DIMENSION/6; 
    final static String MAP_KEY = "AIzaSyC7BbLwsqBDyUcTwF9XwwVODBSWMwRL9Y8";
    final static String DIRECTIONS_KEY = "AIzaSyALnpf0Hp54Lymy00qHvGlTGYYMnIqLAmQ";
    final static String REGION = "uk";
    final static String MODE = "walking";
    final static String MAP_LOCATION = "assets/map.png";
    final static double MAP_UPDATE_TOLERANCE = 0.00005;
    final static int MAP_REFRESH_TIMER = 3000;  // Milliseconds.
    
    final static String HTTP_METHOD = "GET";
    final static String URL_ENCODING = "UTF-8";
    final static int HTTP_TIMEOUT = 5000;    // MS.
    final static int HTTP_BUFFSIZE = 4096;   // 4KB.  
    
    final static String FILE_NAME = "/dev/cu.usbmodem1421";
    final static String FONT = "Arial";
    final static int BUFF_SIZE = 1024;
    final static int EARTH_RADIUS = 6371;
    
    final static String DINO_LOCATION = "assets/dino.png";
    
    final static String DECIMAL_FORMAT = "#0.00";
}
