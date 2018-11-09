
package extrex;

import java.util.Observable;

/**
 *
 * @author Jordan Tucker 2018
 */
abstract class Model extends Observable{
    
    public Model(){}
    abstract void powerButtonClicked();
    abstract void plusButtonClicked();
    abstract void minusButtonClicked();
    abstract void menuButtonClicked();
    abstract void selectButtonClicked();
}
