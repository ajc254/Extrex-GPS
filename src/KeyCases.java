package extrex;

/**
 * Enum class to keep track of the exceptional key cases.
 * @author Louise Haggett 2018
 */
enum KeyCases {
    
    LEFTARROW("⇐"), RIGHTARROW("⇒"), DELETE("DEL"), SPACE("␣");

    private final String key;

    private KeyCases(String key) {
        this.key = key;
    }

    /**
     * Method to get the name of the key
     * @return key the name of the key 
     */
    public String getKey() {
        return key;
    }
}
