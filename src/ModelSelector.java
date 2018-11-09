package extrex;

/**
 * Enum class to select the panel to be viewed.
 *
 * @author Louise Haggett 2018
 */
enum ModelSelector {
    
    WHERETO("Where", 2, "W"), TRIPCOMP("TripComp", 3, "T"), MAP("Map", 1, "M"), SPEECH("Speech", 0, ""), SATELLITE("Sat", 4, "S"), ABOUT("About", 0, "A");

    private final int selector;
    private final String name;
    private final String panel;

    private ModelSelector(String name, int selector, String panel) {
        this.name = name;
        this.selector = selector;
        this.panel = panel;
    }

    /**
     * Method to get the name of the panel
     * @return name the name of the panel 
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get number of the panel
     * @return selector the number of the panel
     */
    public int getSelector() {
        return selector;
    }

    /**
     * Method to get the panel
     * @return panel the panel to be viewed
     */    
    public String getPanel() {
        return panel;
    }
}
