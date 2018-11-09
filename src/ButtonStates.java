package extrex;

/**
 * Enum class to keep track of the button states.
 * @author Louise Haggett 2018
 */
enum ButtonStates {
    
    PLUS("+"), MINUS("-"), SELECT("Select"), POWER("Power"), MENU("Menu");

    private final String name;

    private ButtonStates(String name) {
        this.name = name;
    }

    /**
     * Method to get the name of the button.
     * @return name the name of the button
     */
    public String getName() {
        return name;
    }

}
