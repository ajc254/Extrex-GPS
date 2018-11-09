package extrex;

import javax.swing.JFrame;

/**
 * HomeMVC
 * 
 * @author Romemary Naish 2018
 * @author Jordan Tucker 2018
 * @author Alex Cossins 2018
 */
class HomeMVC {
    
    static MapMVC mapMVC;
    static WhereToMVC whereToMVC;
    static TripComputerMVC tripComputerMVC;
    static SatelliteMVC satelliteMVC;
    
    /**
     * Assemble a HomeMVC.
     * 
     * @param argv Program arguments (Doesn't take any)
     */
    public static void main(String[] argv) {
        
        mapMVC = new MapMVC();
        whereToMVC = new WhereToMVC();
        tripComputerMVC = new TripComputerMVC();
        satelliteMVC = new SatelliteMVC();
        
        HomeModel model = new HomeModel();
        HomeController controller = new HomeController(model);
        HomeView view = new HomeView(controller, model);
        
        // Instantiating and formatting a new JFrame on which the extrex device is displayed
        JFrame jframe = new JFrame();
        jframe.setUndecorated(true);
        jframe.add(view);
        jframe.setSize(Const.FRAME_WIDTH, Const.FRAME_HEIGHT);
        jframe.setLocationRelativeTo(null);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setResizable(false);
        jframe.setVisible(true);
    }
    
}