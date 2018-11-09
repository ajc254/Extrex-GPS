package extrex;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * @author Jordan Tucker 2018
 */
class AboutView extends JPanel {

    final JLabel device = new JLabel("XTrek", SwingConstants.CENTER);
    final JLabel version = new JLabel("7.01a", SwingConstants.CENTER);
    final JLabel copyright = new JLabel("(c) 2018", SwingConstants.CENTER);
    final JLabel title = new JLabel("Dinosoft", SwingConstants.CENTER);
    Font font = new Font(Const.FONT, Font.BOLD, 25);
    BufferedImage dinoImage;
    final String dinoLocation = Const.DINO_LOCATION;
    JLabel dino;

    /**
     * Constructor produces the about view page.
     */
    public AboutView() {

        // Try to find the dino png file from assets.
        try {
            dinoImage = ImageIO.read(new File(dinoLocation));
            dino = new JLabel(new ImageIcon(dinoImage));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }

        // Set the panel properties.
        setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.BLACK));
        setBackground(Color.WHITE);
        setLayout(null);

        // Place all the items in their correct place.
        device.setFont(font);
        device.setBounds(5, 60, 100, 50);

        version.setFont(font);
        version.setBounds(Const.PANEL_WIDTH - 100, 60, 100, 50);

        copyright.setFont(font);
        copyright.setBounds((Const.PANEL_WIDTH/2)-112 , Const.PANEL_HEIGHT - 90, 225, 50);

        title.setFont(font);
        title.setBounds((Const.PANEL_WIDTH/2)-112, Const.PANEL_HEIGHT - 60, 225, 50);

        dino.setBounds(10, 0, Const.PANEL_WIDTH, Const.PANEL_HEIGHT - 55);

        // Show all of the items.
        add(dino);
        add(device);
        add(version);
        add(title);
        add(copyright);
    }
}
