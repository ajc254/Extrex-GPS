package extrex;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

/**
 * HomeView
 *
 * @author Rosemary Naish 2018
 * @author Louise Haggett 2018
 */
class HomeView extends JPanel implements Observer {

    // Initialisation of all desired variables and components.
    
    // Current option count
    int count;
    
    // Total number to loop through
    int maxVal;
    boolean on;
    String opt;
    String[] OptString;
    ArrayList<OptionButton> OptionsLabel;
    
    JLabel imgLabel;
    JLayeredPane layer;
    JPanel imagePanel;
    JPanel buttonPanel;
    JPanel mainPanel;
    JPanel panelOff;
    JPanel panelOn;
    CardLayout card;
    GridBagConstraints gridCon;
    GridBagLayout grid;

    HomeModel Gmodel;
    HomeController Gcon;

    // Initialisation of buttons.
    final OperatorButton plus;
    final OperatorButton minus;
    final OperatorButton select;
    final OperatorButton menu;
    final OperatorButton power;

    final OptionButton where;
    final OptionButton tripcomp;
    final OptionButton map;
    final OptionButton speech;
    final OptionButton sat;
    final OptionButton about;

    static WhereToMVC w = null;
    static SatelliteMVC st = null;
    static TripComputerMVC t = null;
    static AboutView a = null;

    /**
     * Constructor to create a HomeView.
     *
     * @param model the model that connects to the view.
     * @param controller the controller that connects to the view.
     * @author Louise Haggett 2018 (enums)
     */
    public HomeView(HomeController controller, HomeModel model) {

        Gmodel = model;
        Gcon = controller;

        // Instantiation of all new components required for view constructor.
        plus = new OperatorButton(ButtonStates.PLUS.getName());
        minus = new OperatorButton(ButtonStates.MINUS.getName());
        select = new OperatorButton(ButtonStates.SELECT.getName());
        menu = new OperatorButton(ButtonStates.MENU.getName());
        power = new OperatorButton(ButtonStates.POWER.getName());

        where = new OptionButton(ModelSelector.WHERETO.getName());
        tripcomp = new OptionButton(ModelSelector.TRIPCOMP.getName());
        map = new OptionButton(ModelSelector.MAP.getName());
        speech = new OptionButton(ModelSelector.SPEECH.getName());
        sat = new OptionButton(ModelSelector.SATELLITE.getName());
        about = new OptionButton(ModelSelector.ABOUT.getName());

        imagePanel = new JPanel();
        buttonPanel = new JPanel();
        card = new CardLayout();
        mainPanel = new JPanel();
        panelOff = new JPanel();
        panelOn = new JPanel();
        gridCon = new GridBagConstraints();
        grid = new GridBagLayout();
        layer = new JLayeredPane();
        imgLabel = new JLabel(new ImageIcon("assets/extrexSkin.png"));
        
        // Setting panels their layout managers.
        mainPanel.setLayout(card);
        panelOff.setLayout(grid);
        layer.setLayout(new OverlayLayout(layer));
        
        // Adding and formatting the components in each panel.
        panelOn = panelOn(panelOn);
        buttonPanel = buttonPanel(buttonPanel);

        // Adding both panels to CardLayout manager main Panel with associated String value.
        mainPanel.add(panelOff, "OFF");
        mainPanel.add(panelOn, "ON");

        // Choosing the default panel that is shown when the application loads.
        card.show(mainPanel, "OFF");

        // Setting panels to their desired sizing and background colour.
        mainPanel.setVisible(true);
        mainPanel.setSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        panelOff.setVisible(true);
        imagePanel.add(imgLabel);
        
        // One combined view that holds all panels corretly layered. 
        add(layer, BorderLayout.CENTER);
        layer.add(imagePanel, 1);
        layer.add(buttonPanel, 2);

        count = 0;
        
        // Total number to loop through
        maxVal = 5;
        on = false;
        panelOff.setBackground(Color.BLACK);
        OptString = new String[]{ModelSelector.WHERETO.getName(), ModelSelector.TRIPCOMP.getName(),
                    ModelSelector.MAP.getName(), ModelSelector.SPEECH.getName(),
                    ModelSelector.SATELLITE.getName(), ModelSelector.ABOUT.getName()};
       
        OptionsLabel = new ArrayList();
        OptionsLabel.addAll(Arrays.asList(where, tripcomp, map, speech, sat, about));

        // The observer that allows the update method to recieve notifications from elsewhere.
        model.addObserver(this);
    }

    /**
     * A method to update and add elements to the on panel. Allows for clearer
     * code and makes details easier to change as they are all in the same
     * place.
     *
     * @param panel the panel which needs to be updated
     * @return the panel with all the correct elements
     */
    JPanel panelOn(JPanel panel) {

        /*
        * Refactored by Louise Haggett 2018
         */
        panelOn.setLayout(new GridLayout(3, 2, 0, 0));
        panelOn.setVisible(false);

        gridCon.gridx = 0;
        gridCon.gridy = 0;
        panelOn.add(where);
        gridCon.gridx = 1;
        panelOn.add(tripcomp);
        gridCon.gridx--;
        gridCon.gridy++;
        panelOn.add(map);
        gridCon.gridx++;
        panelOn.add(speech);
        gridCon.gridx = 0;
        gridCon.gridy++;
        panelOn.add(sat);
        gridCon.gridx++;
        panelOn.add(about);

        return panel;
    }

    /**
     * A method to update and add elements to the button panel. Allows for
     * clearer code and makes details easier to change as they are all in the
     * same place.
     *
     * @param panel the panel which needs to be updated
     * @return the panel with all the correct elements
     */
    JPanel buttonPanel(JPanel panel) {

        buttonPanel.setOpaque(false);

        buttonPanel.setVisible(true);
        buttonPanel.setSize(new Dimension(Const.FRAME_WIDTH, Const.FRAME_HEIGHT));
        buttonPanel.setLayout(null);
        
        // All buttons are individually set within the panel.
        plus.setBounds(5, 100, 28, 82);
        buttonPanel.add(plus);

        minus.setBounds(5, 185, 28, 82);
        buttonPanel.add(minus);

        select.setBounds(5, 310, 28, 88);
        buttonPanel.add(select);

        mainPanel.setBounds(114, 350, 100, 60);
        buttonPanel.add(mainPanel);

        power.setBounds(340, 167, 80, 84);
        buttonPanel.add(power);

        menu.setBounds(500, 120, 28, 90);
        buttonPanel.add(menu);

        return panel;

    }

    class OptionButton extends JLabel {

        /**
         * Creates button with an associated string name and a mouse listener
         * The method will also deal with mouse click events on each created
         * button and groups options together
         *
         * @param s The string that is passed through to name the created button
         * and is later used within the switch statement.
         */
        OptionButton(String s) {
            setIcon(new ImageIcon("assets/" + s + "Icon.PNG"));
            setOpaque(true);
        }
    }

    class OperatorButton extends JButton {

        /**
         * Creates a button with an associated string name and a mouse listener
         * The method will also deal with mouse click events on each created
         * button and groups operators together.
         *
         * @param s The string that is passed through to name the created button
         * and is later used within the switch statement.
         */
        OperatorButton(String s) {
            super(s);
            setOpaque(false);
            addMouseListener(Gcon);

        }
    }

    /**
     * A method which updates the view when the power is turned on and enables
     * the buttons accordingly.
     */
    public void enable() {
        
        setNew();
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        on = true;
        cl.show(mainPanel, "ON");
        plus.setEnabled(true);
        minus.setEnabled(true);
        select.setEnabled(true);
        menu.setEnabled(true);
    }

    /**
     * A method which updates the view when the power is off and disables the
     * buttons accordingly.
     */
    public void disable() {
        
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        on = false;
        cl.show(mainPanel, "OFF");
        plus.setEnabled(false);
        minus.setEnabled(false);
        select.setEnabled(false);
        menu.setEnabled(false);
        reset();
    }

    /**
     * A method to revert back to the original image icon upon an option being
     * deselected.
     *
     */
    public void setOld() {
        OptionsLabel.get(count).setIcon(new ImageIcon("assets/" + OptString[count] + "Icon.PNG"));
    }

    /**
     * A method to show a new image icon upon an option being selected.
     */
    public void setNew() {
        OptionsLabel.get(count).setIcon(new ImageIcon("assets/" + OptString[count] + "IconSEL.PNG"));
    }

    /**
     * A method that resets the on screen display to a default layout and is
     * called when the screen is turned back on.
     */
    public void reset() {
        
        setOld();
        count = 0;
        setNew();
    }

    /**
     * A method which will switch the view back to the on screen on menu press.
     */

    public void menu() {
        
        if (on) {
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            cl.show(mainPanel, "ON");
        }
    }

    /**
     * A method that decrements the chosen option on minus button clicked and
     * will wrap round.
     */
    public void minusButton() {
        
        setOld();
        if (this.count == 0) {
            this.count = this.maxVal;
        } else {
            this.count -= 1;
        }
        setNew();
    }

    /**
     * A method that increments the chosen option on plus button clicked and
     * will wrap round.
     */
    public void plusButton() {
        
        setOld();
        if (count == maxVal) {
            count = 0;
        } else {
            count += 1;
        }
        setNew();
    }

    /**
     * A method to update the current visible panel on the device.
     *
     * @param panel The panel which is to be displayed.
     * @param s the string associated with each panel, used to switch between
     * layouts.
     */
    public void updateView(JPanel panel, String s) {
        
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        mainPanel.add(panel, s);
        cl.show(mainPanel, s);
    }

    /**
     * A method to decide which panel should be switched to dependent on which
     * panel is selected.
     *
     * @author Jordan Tucker 2018
     * @author Louise Haggett 2018 (using enums)
     *
     * @param model the model to be used to display the panel
     */
    public void changePanel(int model) {
        
        ModelSelector modelSelected = ModelSelector.values()[model];
        switch (modelSelected) {
            case MAP:
                Gcon.modelSelector = ModelSelector.MAP.getSelector();
                HomeMVC.satelliteMVC.resumeSatellite();
                HomeMVC.mapMVC.resumeMap();
                updateView(HomeMVC.mapMVC, ModelSelector.MAP.getPanel());
                break;

            case WHERETO:
                Gcon.modelSelector = ModelSelector.WHERETO.getSelector();
                updateView(HomeMVC.whereToMVC, ModelSelector.WHERETO.getPanel());
                break;

            case TRIPCOMP:
                Gcon.modelSelector = ModelSelector.TRIPCOMP.getSelector();
                HomeMVC.satelliteMVC.resumeSatellite();
                updateView(HomeMVC.tripComputerMVC, ModelSelector.TRIPCOMP.getPanel());
                break;

            case SATELLITE:
                Gcon.modelSelector = ModelSelector.SATELLITE.getSelector();
                HomeMVC.satelliteMVC.resumeSatellite();
                updateView(HomeMVC.satelliteMVC, ModelSelector.SATELLITE.getPanel());
                break;

            case SPEECH:
                break;

            case ABOUT:
                if (a == null) {
                    a = new AboutView();
                }
                updateView(a, ModelSelector.ABOUT.getPanel());
                break;
        }
    }

    /**
     * An overridden update method which is notified with a string, which it
     * uses to string match to recognise and carry out the appropriate action to
     * match the button press.
     *
     * @author Rosemary Naish 2018
     * @author Louise Haggett 2018 (using enums)
     * @param o The observer.
     * @param arg The argument passed by the notify observers method used
     * elsewhere.
     */
    @Override
    public void update(Observable o, Object arg) {
        
        if (arg instanceof ButtonStates) {
            if (on) {
                switch ((ButtonStates) arg) {

                    case POWER:
                        disable();
                        break;

                    case MENU:
                        menu();
                        break;

                    case PLUS:
                        plusButton();
                        break;

                    case MINUS:
                        minusButton();
                        break;

                    case SELECT:
                        changePanel(count);
                }
            } else {
                switch ((ButtonStates) arg) {
                    case POWER:
                        enable();
                }
            }
        }
    }
}
