import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
/**
 * Write a description of class GameData here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameData  
{

    private static int level;
    private static Scanner scan;
    private static StringTokenizer tokenizer;
    private static Player player = new Player();
    public static final String SAVE_FILE = "saves.txt";
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
    public static String getDataString() {
        return level + "~" + player.toString();
    }
    /**
     * Writes the save into a file.
     */
    public static void exportData() {
        String data = getDataString();
        
        try {
            FileWriter out = new FileWriter(SAVE_FILE);
            PrintWriter output = new PrintWriter(out);
            output.println(data);
            output.close();
            out.close();
        } catch (IOException e) {
            System.out.println("err: something went wrong when writing save to file...");
        }
    }
    public static boolean importData() {
        try {
            Scanner scan = new Scanner(new File(SAVE_FILE));
            String save = null;
            if (scan.hasNextLine()) save = scan.nextLine();
            if (null == save) {
                System.out.println("warn: save file has no save, starting with new save");
                resetData();
            }
            return loadData(save);
        }
        catch (FileNotFoundException e) {
            System.out.println("warn: save file not found, starting with new save");
            resetData(); // Use defaults
            return false;
        }
    }
    /**
     * Given the string representation of a player, load the game data from there.
     */
    public static boolean loadData(String data) {
        // Split to level data at index 0, and player data at index 1.
        String[] params = data.split("~");
        try {
            level = Integer.valueOf(params[0]);
        } catch (NumberFormatException e) {
            if (GameWorld.SHOW_LOGS) System.out.println("err: unexpected level when loading game data");
            resetData();
            return false;
        }
        player.loadPlayerData(params);
        StatsUI.loadStatsData(params);
        return true;
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
