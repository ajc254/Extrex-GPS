package extrex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Jordan Tucker 2018
 */
class SatelliteView extends JPanel implements Observer {

    final JLabel latField = new JLabel("", SwingConstants.RIGHT);
    final JLabel lngField = new JLabel("", SwingConstants.RIGHT);
    final JLabel modemStatus = new JLabel("", SwingConstants.CENTER);
    
    private String lat;
    private String lng;
            
    /**
     * @param model the model to interact with by the view.
     */
    public SatelliteView(SatelliteModel model) {
        
        // Set the properties of the panel.
        setPreferredSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLACK));
        setBackground(Color.WHITE);
        setLayout( null );
        
        // Set up the font for both the text fields.
        Font font = new Font(Const.FONT, Font.BOLD,34);
        Font errorFont = new Font(Const.FONT, Font.BOLD, 18);
        
        latField.setFont(font);
        lngField.setFont(font);
        modemStatus.setFont(errorFont);
        
        modemStatus.setForeground(Color.RED);
        
        latField.setBounds(10, 140, 235, 75);
        lngField.setBounds(10, 200, 235, 75);
        modemStatus.setBounds(0, 20, Const.PANEL_WIDTH, 50);
   
        // Add the text fields to the panel.
        add(latField);
        add(lngField);
        add(modemStatus);

        model.addObserver(this);
    }

    /**
     * @param latlng An object that holds both the latitude and longitude to 
     *               be put onto the screen.
     */
    public void updateCoords(String[][] latlng) {
        lat = latlng[0][0] + " " + latlng[0][1];
        lng = latlng[1][0] + " " + latlng[1][1];
        latField.setText(lat);
        lngField.setText(lng);
        if(!Satellite.hasModem()){
            modemStatus.setText("Modem Not Available");
        }else if(!Satellite.hasSignal()){
            modemStatus.setText("<html>No Signal <br> Showing last known position</html>");
        }else{
            modemStatus.setText("");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateCoords((String[][]) arg);
    }
}
