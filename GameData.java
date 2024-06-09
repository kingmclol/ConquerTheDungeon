/**
 * Write a description of class GameData here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameData  
{

    private static int level;
    private static Player player = new Player();
    /**
     * Constructor for objects of class GameData
     */
    private GameData()
    {
    }
    /**
     * Increments the level by 1.
     */
    public static void incrementLevel() {
        level++;
    }
    /**
     * Resets the game related data to default states.
     */
    public static void resetData() {
        level = 0;
        player = new Player();
    }
    /**
     * Returns a string representation of the game state.
     */
    public static String exportData() {
        // tba
        return "banana";
    }
    /**
     * Given the string representation of a game, load the game data from there.
     */
    public static void loadData() {
        // tba
    }
    /**
     * Gets the main Player object of the game.
     */
    public static Player getPlayer() {
        return player;
    }
    /**
     * Gets the current level of the game.
     */
    public static int getLevel() {
        return level;
    }
}
