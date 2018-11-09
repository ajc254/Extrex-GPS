package extrex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * TripComputer View.
 *
 * @author Louise Haggett 2018
 */
class TripComputerView extends JPanel implements Observer {

    final JLabel kilometres = new JLabel("trip odem", SwingConstants.CENTER);
    final JLabel speed = new JLabel("speed", SwingConstants.CENTER);
    final JLabel time = new JLabel("moving time", SwingConstants.CENTER);
    final JLabel kmField = new JLabel();
    final JLabel speedField = new JLabel();
    final JLabel timeField = new JLabel();

    /**
     * @param model the model to interact with the view.
     */
    public TripComputerView(TripComputerModel model) {

        // Set the properties of the panel.
        setPreferredSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLACK));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(6, 1));
        Font font = new Font(Const.FONT, Font.BOLD, 40);

        // Set the properties for the kilometres JLabel.
        kilometres.setFont(font);
        kilometres.setOpaque(true);
        kilometres.setBackground(Color.WHITE);

        // Set the properties for the speed JLabel.
        speed.setFont(font);
        speed.setOpaque(true);
        speed.setBackground(Color.WHITE);
        speed.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.BLACK));

        // Set the properties for the time JLabel.
        time.setFont(font);
        time.setOpaque(true);
        time.setBackground(Color.WHITE);
        time.setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.BLACK));

        // Set the properties for the kmField JLabel.
        kmField.setFont(font);
        kmField.setBackground(Color.WHITE);
        kmField.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));
        kmField.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the properties for the speedField JLabel.
        speedField.setFont(font);
        speedField.setBackground(Color.WHITE);
        speedField.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Color.BLACK));
        speedField.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the properties for the timeField JLabel.
        timeField.setFont(font);
        timeField.setBackground(Color.WHITE);
        timeField.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the fields to the panel.
        add(kilometres);
        add(kmField);
        add(speed);
        add(speedField);
        add(time);
        add(timeField);

        model.addObserver(this);
    }

    /**
     * @param tripComp An object that holds both the latitude and longitude to
     * be put onto the screen.
     */
    public void updateSDT(String[] tripComp) {
        
        kmField.setText(tripComp[0]);
        speedField.setText(tripComp[2]);
        timeField.setText(tripComp[1]);
    }

    @Override
    public void update(Observable o, Object arg) {
        
        try {
            updateSDT((String[]) arg);
        } catch (Exception e) {
            System.out.println("Button Disabled");
        }
    }

}
