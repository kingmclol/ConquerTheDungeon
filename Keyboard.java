import greenfoot.*;
/**
 * <p>This class manages getting the current key that was released</p>
 * 
 * <p></strong>the method update() MUST be run every act ONCE</strong></p>
 * 
 * @author Osmond
 */
public class Keyboard {
    private static String currentKey = "";

    public static void update() {
        currentKey = Greenfoot.getKey();
    }
    /**
     * Returns the key that was just released
     */
    public static String getCurrentKey() {
        return currentKey;
    }
}
