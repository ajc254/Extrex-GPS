package extrex;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

/**
 * HomeController
 * 
 * @author Alexander Cossins and Jordan Tucker 2018
 */
class HomeController implements MouseListener{
    
    int modelSelector;
    private HomeModel homeModel;
    List<Model> models;
    
    /**
      * Constructor for HomeController.
     * @param homeModel The instance of the model upon which the controller is acting.
      */
    public HomeController(HomeModel homeModel) {
        this.models = new ArrayList<>();
        this.homeModel = homeModel;
        models.add(homeModel);
        models.add(HomeMVC.mapMVC.getModel());
        models.add(HomeMVC.whereToMVC.getModel());
        models.add(HomeMVC.tripComputerMVC.getModel());
        models.add(HomeMVC.satelliteMVC.getModel());
    }
    
    /**
     * A method to deal with a mouse press detected. It will convert the source of the button
     * to text which can then be string matched through a string statement to decide on the course of action.
     * 
     * @param e A mouse event that is triggered whenever a mouse press is registered. 
     */
    public void mousePressed ( MouseEvent e ) {
        
        JButton button = (JButton) e.getSource();
        String s = button.getText();
        switch(s){
            case "Power":
                // @author Alexander Cossins 2018
                for (Model m : models) {
                    m.powerButtonClicked();
                }
                modelSelector = 0;
                break;
            case "+" :
                models.get(modelSelector).plusButtonClicked();
                break;
            case "-":
                models.get(modelSelector).minusButtonClicked();
                break;
            case "Menu":
                // If the current panel shown is not the menu, switch to the menu
                if (modelSelector != 0) {
                    models.get(modelSelector).menuButtonClicked();
                    modelSelector = 0;
                    homeModel.menuButtonClicked();
                }
                else {
                    homeModel.menuButtonClicked();
                }
                break;
            case "Select":
                models.get(modelSelector).selectButtonClicked();
                break;
        }
    }
    
    /*
     * Methods must be overwritten due to the contract with MouseListener
    */
    public void mouseClicked ( MouseEvent e ) { /* nothing */ }
    public void mouseEntered ( MouseEvent e ) { /* nothing */ }
    public void mouseExited  ( MouseEvent e ) { /* nothing */ }
    public void mouseReleased( MouseEvent e ) { /* nothing */ }
}
