package extrex;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * @author Louise Haggett 2018
 */
class WhereToMVC extends JPanel {
    HomeModel model;
    
    /**
     * Method to assemble a WhereToMVC.
     */
    public WhereToMVC() {
        model = new HomeModel();
        WhereToController controller = new WhereToController(model);
        WhereToView view = new WhereToView(model, controller);

        setBackground(Color.BLACK);
        
        setPreferredSize(new Dimension(Const.PANEL_WIDTH, Const.PANEL_HEIGHT));
        add(view);
        setVisible(true);
    }
    
    HomeModel getModel() {
        return model;
    }
}
