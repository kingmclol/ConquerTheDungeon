import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class GameData  
{

    private static int level;
    private static Scanner scan;
    private static StringTokenizer tokenizer;
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
        return player.exportPlayer();
    }
    /**
     * Given the string representation of a player, load the game data from there.
     */
    public static void loadData(String data) {
        tokenizer = new StringTokenizer(data, ",");
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
