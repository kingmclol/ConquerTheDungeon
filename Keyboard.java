import greenfoot.*;

public class Keyboard {
    private static String currentKey = "";

    public static void update() {
        currentKey = Greenfoot.getKey();
    }

    public static String getCurrentKey() {
        return currentKey;
    }
}
