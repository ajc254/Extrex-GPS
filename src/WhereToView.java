package extrex;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * WhereToView
 *
 * @author Louise Haggett 2018
 */
class WhereToView extends JPanel implements Observer {

    static final JTextField text = new JTextField();
    static String destination = "";
    Font font = new Font(Const.FONT, Font.BOLD, 23);
    Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
    JPanel rootPanel = new JPanel();
    static List<PanelKey> panel1Keys = new ArrayList<>();
    static List<PanelKey> panel2Keys = new ArrayList<>();
    JPanel panel1 = new AlphabetKeyboard();
    JPanel panel2 = new NumericalKeyboard();
    static int currentPanel = 0;
    static int panel1Count = 0;
    static int panel2Count = 0;

    /**
     * Constructor to create a WhereToView.
     *
     * @param model The model connecting to the view.
     * @param controller The controller connecting to the view.
     */
    public WhereToView(HomeModel model, WhereToController controller) {
        setPreferredSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setLayout(null);

        // Add the panels to the view.
        add(rootPanel);
        add(panel1);
        add(panel2);

        // Format the text field.
        text.setEditable(false);
        text.setFont(font);
        text.setBounds(Const.TEXT_X, Const.TEXT_Y, Const.KEYBOARD_WIDTH, Const.TEXT_HEIGHT);
        text.setBackground(Color.WHITE);

        // Format the root panel.
        rootPanel.setLayout(null);
        rootPanel.setSize(Const.PANEL_WIDTH, Const.INNER_PANEL_HEIGHT);
        rootPanel.setBackground(Color.BLACK);
        rootPanel.add(text);
        rootPanel.setVisible(true);

        model.addObserver(this);
    }

    /**
     * Nested class to create an AlphabetKeyboard.
     */
    public class AlphabetKeyboard extends JPanel {

        List<PanelKey> alphabetKeyboard = new ArrayList<>();
        
        /**
         * Constructor to create the AlphabetKeyboard.
         */
        AlphabetKeyboard() {

            // Instantiating the buttons to be added to the panel.
            for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
                PanelKey key = new PanelKey(Character.toString(alphabet));
                panel1Keys.add(key);
                alphabetKeyboard.add(key);
            }
            final PanelKey space = new PanelKey(KeyCases.SPACE.getKey());
            final PanelKey right = new PanelKey(KeyCases.RIGHTARROW.getKey());
            panel1Keys.add(space);
            panel1Keys.add(right);
            
            // Set the first key to be orange.
            panel1Keys.get(0).setBackground(Color.ORANGE);
            
            // Use a GridLayout to add the buttons to the panel.
            setLayout(new GridLayout(Const.KEY_ROWS, Const.KEY_COLUMNS, 0, 0));
            setVisible(true);
            setBounds(Const.KEYBOARD_X, Const.KEYBOARDY, Const.KEYBOARD_WIDTH, Const.KEYBOARD_HEIGHT);

            // Add the buttons to the panel.
            for (PanelKey i : alphabetKeyboard) {
                add(i);
            }
            add(space);
            add(right);
        }
    }

    /**
     * Nested class to create a NumericalKeyboard.
     */
    public class NumericalKeyboard extends JPanel {

        ArrayList<PanelKey> numberKeyboard = new ArrayList<>();
        
        /**
         * Constructor to create the NumericalKeyboard.
         */
        NumericalKeyboard() {
            GridBagConstraints gridCon = new GridBagConstraints();

            // Instantiating the buttons to be added to the panel.
            for (int i = 1; i < 10; i++) {
                PanelKey key = new PanelKey(Integer.toString(i));
                panel2Keys.add(key);
                numberKeyboard.add(key);
            }
            
            final PanelKey btn0 = new PanelKey("0");
            final PanelKey left = new PanelKey(KeyCases.LEFTARROW.getKey());
            final DeleteKey del = new DeleteKey(KeyCases.DELETE.getKey());

            panel2Keys.add(btn0);
            panel2Keys.add(left);
            panel2Keys.add(del);

            panel2Keys.get(0).setBackground(Color.ORANGE);
                        
            setBounds(Const.KEYBOARD_X, Const.KEYBOARDY, Const.KEYBOARD_WIDTH, Const.KEYBOARD_HEIGHT);
            setVisible(false);

            // Use a GridBagLayout to add the buttons to the panel.
            setLayout(new GridBagLayout());
            gridCon.fill = GridBagConstraints.HORIZONTAL;
            gridCon.weightx = 0;
            gridCon.weighty = 0;

            // Row 1 of the panel.
            gridCon.gridx = 0;
            gridCon.gridy = 0;
            for (int i = 0; i < 3; i++) {
                add(numberKeyboard.get(i), gridCon);
                gridCon.gridx++;
            }

            //Row 2 of the panel.
            gridCon.gridx = 0;
            gridCon.gridy = 1;
            for (int i = 3; i < 6; i++) {
                add(numberKeyboard.get(i), gridCon);
                gridCon.gridx++;
            }

            // Row 3 of the panel.
            gridCon.gridx = 0;
            gridCon.gridy = 2;
            for (int i = 6; i < 9; i++) {
                add(numberKeyboard.get(i), gridCon);
                gridCon.gridx++;
            }

            // Row 4 of the panel.
            gridCon.gridx = 0;
            gridCon.gridy++;
            add(btn0, gridCon);
            gridCon.gridy++;
            add(left, gridCon);

            // Make the delete button twice as big as the other buttons.
            gridCon.gridx++;
            gridCon.gridy--;
            gridCon.gridwidth = 2;
            gridCon.gridheight = 2;
            add(del, gridCon);
        }
    }

    /**
     * Nested class to create a PanelKey.
     */
    private class PanelKey extends JLabel {

        /**
         * Constructor to create a PanelKey. This method will be used to create
         * a base for the keys on the keyboard.
         *
         * @param s The string label of the key.
         */
        PanelKey(String s) {
            super(s);
            setOpaque(true);
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(Const.KEY_WIDTH, Const.KEY_HEIGHT));
            setFont(font);
            setBorder(border);
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    /**
     * Nested class to create a DeleteKey.
     * NOTE: This class is needed as the delete key is twice the size of the other buttons.
     */
    private class DeleteKey extends PanelKey {

        /**
         * Constructor to create a DeleteKey, when this key is clicked, the last
         * character of the string is deleted.
         *
         * @param s The string that is getting a space deleted from the end of it.
         * 
         */
        DeleteKey(String s) {
            super(s);
            setPreferredSize(new Dimension(Const.KEY_WIDTH * 2, Const.KEY_HEIGHT * 2));
        }
    }
    
    /**
     * Method to determine which key the user is on if they have selected the minus button.
     * The key that the user is now on will be highlighted orange.
     */
    public void minusButton() {
        
        // If the user is on the AlphabetKeyboard.
        if (currentPanel == 0) {
            panel1Keys.get(panel1Count).setBackground(Color.WHITE);
            if (panel1Count == 0) {
                panel1Count = Const.PANEL_1_MAX_VAL;
            } else {
                panel1Count -= 1;
            }
            
            // Set the current key to orange.
            panel1Keys.get(panel1Count).setBackground(Color.ORANGE);
        } else {
            
            // If the user is on the NumericalKeyboard.
            panel2Keys.get(panel2Count).setBackground(Color.WHITE);
            if (panel2Count == 0) {
                panel2Count = Const.PANEL_2_MAX_VAL;
            } else {
                panel2Count -= 1;
            }
            panel2Keys.get(panel2Count).setBackground(Color.ORANGE);

        }
    }

    /**
     * Method to determine which key the user is on if they have selected the plus button.
     * The key that the user is now on will be highlighted orange.
     */
    public void plusButton() {
        
        if (currentPanel == 0) {
            panel1Keys.get(panel1Count).setBackground(Color.WHITE);
            if (panel1Count == Const.PANEL_1_MAX_VAL) {
                panel1Count = 0;
            } else {
                panel1Count += 1;
            }
            panel1Keys.get(panel1Count).setBackground(Color.ORANGE);
        } else {
            panel2Keys.get(panel2Count).setBackground(Color.WHITE);
            if (panel2Count == Const.PANEL_2_MAX_VAL) {
                panel2Count = 0;
            } else {
                panel2Count += 1;
            }
            panel2Keys.get(panel2Count).setBackground(Color.ORANGE);

        }
    }
 
    /**
     * Method to switch the panel that is viewed by the user if the arrow key is selected.
     */
    public void switchPanel() {
        
        if (currentPanel == 0) {
            panel1.setVisible(false);
            panel2.setVisible(true);
            currentPanel = 1;
        } else {
            panel1.setVisible(true);
            panel2.setVisible(false);
            currentPanel = 0;
        }
    }

    /**
     * Method to determine what action should be taken depending on the key selected by the user.
     * 
     * @author Louise Haggett 2018
     * @author Jordan Tucker 2018 
     */
    public void selectButton() {
        
        String panelValue;
        if (currentPanel == 0) {
            panelValue = panel1Keys.get(panel1Count).getText();
        } else {
            panelValue = panel2Keys.get(panel2Count).getText();
        }
        switch (panelValue) {
            
            case "⇒":
                switchPanel();
                break;
                
            case "⇐":
                switchPanel();
                break;
                
            case "DEL":
                if (destination.length() != 0) {
                    destination = destination.substring(0, destination.length() - 1);
                }
                text.setText(destination);
                break;
                
            case "␣":
                destination += " ";
                text.setText(destination);
                break;
                
            default:
                destination += panelValue;
                text.setText(destination);
                break;
        }
    }
    
    public static void reset(){
        destination = "";
        text.setText("");
        panel1Keys.get(panel1Count).setBackground(Color.WHITE);
        panel2Keys.get(panel2Count).setBackground(Color.WHITE);
        panel1Keys.get(0).setBackground(Color.ORANGE);
        panel2Keys.get(0).setBackground(Color.ORANGE);
        panel1Count = 0;
        panel2Count = 0;
        currentPanel = 0;
    }

    /**
     * Method to update the value of the text when one of the methods in the
     * controller is called.
     *
     * @param o The observable object.
     * @param arg The new text to be displayed.
     */
    @Override
    public void update(Observable o, Object arg) {
        
        if(arg instanceof ButtonStates) {
            switch ((ButtonStates) arg) {
                
                case PLUS:
                    plusButton();
                    return;
                    
                case MINUS:
                    minusButton();
                    return;
                    
                case SELECT:
                    selectButton();
                    break;
            }
        }
    }
}
