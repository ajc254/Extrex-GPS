package extrex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Alexander Cossins 2018
 */

class MapView extends JPanel implements Observer {
    
    private final JLabel mapLabel;
    private final JLabel wait;
    private BufferedImage currentMapImage;
    private boolean signalLost;
    
    /**
     * Constructor to prepare map label also register as an observer to MapModel.
     * 
     * @param model The model to register this view as an observer to.
     */
    MapView(MapModel model) {
        
        signalLost = false;
        currentMapImage = null;
        
        model.addObserver(this);
        setLayout(null);
        setPreferredSize( new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT) );
        
        // Prepare label for the map.
        mapLabel = new JLabel();
        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mapLabel.setVerticalAlignment(SwingConstants.CENTER);
        mapLabel.setBounds(0, 0, Const.PANEL_WIDTH, Const.PANEL_HEIGHT);
        add(mapLabel);
        
        // Prepare label for the signal waiting screen.
        wait = new JLabel("Waiting for a Signal");
        wait.setFont(new Font(Const.FONT, Font.BOLD, 18));
        wait.setBackground(Color.LIGHT_GRAY);
        wait.setBounds(0, 0, Const.PANEL_WIDTH, Const.PANEL_HEIGHT);
        wait.setVerticalAlignment(SwingConstants.CENTER);
        wait.setHorizontalAlignment(SwingConstants.CENTER);
        wait.setOpaque(true);
        wait.setVisible(true);
        add(wait);
    }
    
    /**
     * Displays a completed map within the prepared label.
     * 
     * @param mapImage The map image to be displayed using the label.
     */
    void displayMap( BufferedImage mapImage ) {
        
        // Utilise graphics to draw a smooth red dot visual to represent location.
        Graphics2D gr = (Graphics2D) mapImage.getGraphics();
        gr.setColor(Color.RED);
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                            RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw in the center of the map.
        gr.fillOval(Const.MAP_DOT_LOCATION, Const.MAP_DOT_LOCATION, 
                    Const.MAP_DOT_DIAMETER, Const.MAP_DOT_DIAMETER);
        
        // Set the new map.
        mapLabel.setIcon(new ImageIcon(mapImage));
        currentMapImage = mapImage;
        
        if (signalLost) {
            displayNoSignal();
        }
    }
    
    /**
     * If there is no GPS signal, notify the user of this by adding a warning to the
     * top of the last known map.
     */
    void displayNoSignal() {

        if (currentMapImage == null) {
            return;
        }
        Graphics2D gr = (Graphics2D) currentMapImage.getGraphics();
        gr.setColor(Color.RED);
        gr.setFont(new Font("default", Font.BOLD, 18));
        gr.drawString("Signal Lost!", Const.SIGNAL_LOST_WIDTH, Const.SIGNAL_LOST_HEIGHT);
        mapLabel.setIcon(new ImageIcon(currentMapImage));
    }
    
    /**
     * If there is no internet connection, or an issue with the HTTP request,
     * display a warning to the top of the last known map.
     * 
     * NOTE: This would also occur if the map API key was rejected.
     */
    void displayNoInternet() {

        if (currentMapImage == null) {
            return;
        }
        Graphics2D gr = (Graphics2D) currentMapImage.getGraphics();
        gr.setColor(Color.RED);
        gr.setFont(new Font(Const.FONT, Font.BOLD, 18));
        gr.drawString("Internet Lost!", Const.CONNECTION_LOST_WIDTH, Const.CONNECTION_LOST_HEIGHT);
        mapLabel.setIcon(new ImageIcon(currentMapImage));
    }
    
    
    void resetState() {
        
        mapLabel.setIcon(null);
        currentMapImage = null;
        signalLost = false;
        mapLabel.revalidate();
    }

    /**
     * Triggered when MapModel changes the map image, the new map is displayed.
     * 
     * @param obs The observable object.
     * @param mapImage The new map image to be displayed. (type : BufferedImage)
     */
    @Override
    public void update(Observable obs, Object mapImage) {
        
        // Check if the update object is a state change.
        if (mapImage instanceof MapStates) {

            switch((MapStates) mapImage) {

                case SIGNAL_LOST:
                    displayNoSignal();
                    signalLost = true;
                    break;

                case SIGNAL_REAQUIRED:
                    signalLost = false;
                    break;

                case CONNECTION_ISSUE:
                    displayNoInternet();
                    break;  
                    
                case CLEAR_STATE:
                    resetState();
                    break;
            }
        }
        
        // If the object was not a state change, instead a new map image.
        else {
            displayMap((BufferedImage) mapImage);
        }
    }
}
