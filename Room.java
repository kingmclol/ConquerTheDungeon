import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.List;

/**
 * <p>The Room superclass is for rooms that the player is neat to be playing in. It holds the roomTemplates, which contains
 * all of the possible random rooms to load from, along with other specific rooms like the shop room.</p>
 * 
 * <p>This class manages the addition of the player onto the board, along with announcing the current level and addition of the
 * bounding boxes of the World.</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public abstract class Room extends GameWorld
{

    /**
     * Constructor for objects of class Room.
     * 
     */
    private static GreenfootSound dungeonSound = new GreenfootSound("dungeonSound.mp3");
    
    private static ArrayList<String> roomTemplates;
    protected int currentLevel;
    protected Timer timer;
    
    protected static final String fallbackBuildString = "16~12~f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/w/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/w/w/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/";
    protected static final String shopBuild = "16~12~f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/shp/f/f/f/f/shp/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/shp/f/f/f/f/shp/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/ptl/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/";
    /**
     * Createss a room of a given level and board.
     */
    public Room(int level, Board b)
    {    
        super();
        board = b;
        addObject(board, 0, 0);
        timer = new Timer();
        currentLevel = level;
        
        board.addEntity(GameData.getPlayer(), board.getRandomSpawnableCell());
        alert("LEVEL " + currentLevel, Color.WHITE, getHeight()-100);
        addBorderBoxes();
        dungeonSound.setVolume(50);
        dungeonSound.playLoop();
    }
    public void started() {
        dungeonSound.playLoop();
    }
    public void stopped() {
        dungeonSound.pause();
    }
    public void act() {
        super.act();
    }
    
    /**
     * Initializes the arraylist that holds all the possible room types that can exist. Must be run
     * VERY early on.
     */
    public static void initializeRoomDatabase() {
        if (roomTemplates != null) return;
        roomTemplates = new ArrayList<String>();
        Scanner scan;
        try {
            scan = new Scanner(new File("buildStrings.txt"));
            while (scan.hasNextLine()) {
                roomTemplates.add(scan.nextLine());
            }
        } catch (Exception e){
            if (GameWorld.SHOW_LOGS) System.out.println("err: missing buildStrings.txt");
            // add a sad world...
            roomTemplates.add(fallbackBuildString);
        }
        if (GameWorld.SHOW_LOGS) System.out.println("info: successfuly loaded in room database");
    }
    /**
     * Returns a random board from the room types that exissts. The Room databse must be initialized first.
     * @return A random board from the RoomDatabase.
     */
    public static Board getRandomBoard() {
        if (roomTemplates == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: attempted to get random board when database not initialized");
            return new Board(fallbackBuildString);
        }
        String buildString = roomTemplates.get(Greenfoot.getRandomNumber(roomTemplates.size()));
        if (GameWorld.SHOW_LOGS) System.out.println(buildString);
        Board b = new Board(buildString);
        return b;
    }
    /**
     * Plays or stops the music depending on the variable given.
     * @param playing whether to play the music
     */
    public static void setMusicState(boolean playing) {
        if (playing) {
            dungeonSound.setVolume(50);
            dungeonSound.playLoop();
        } else {
            dungeonSound.stop();
        }
    }
}
