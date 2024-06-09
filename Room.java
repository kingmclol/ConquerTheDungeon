import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.List;

/**
 * Write a description of class Room here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Room extends GameWorld
{

    /**
     * Constructor for objects of class Room.
     * 
     */
    private static ArrayList<String> roomTemplates;
    private List<List<String>> spawnWaves;
    private int currentLevel;
    private Timer timer;
    private static final int FIRST_WAVE_WAIT = 180;
    private int currentWave;
    private static final int ENEMY_ALIVE_CHECK_PERIOD = 60; // Will check if all enemies are dead every this amount of acts.
    private boolean portalSpawned;
    private static final String fallbackBuildString = "16~12~f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/w/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/w/w/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/";
    
    public Room(int level)
    {    
        super();
        timer = new Timer();
        board = getRandomBoard();
        currentLevel = level;
        addObject(board, 0, 0);
        board.addEntity(GameData.getPlayer(), board.getRandomSpawnableCell());
        spawnWaves = generateSpawnWaves(currentLevel);
        currentWave = 1;
        portalSpawned = false;
        alert("LEVEL " + currentLevel, Color.WHITE, getHeight()-100);
        addBorderBoxes();
    }
    public void act() {
        super.act();
        // Give the player a bit of time to get their bearings first before spawning the first wave.
        if (currentWave == 1 && timer.actsPassed() >= FIRST_WAVE_WAIT) { 
            spawnNextWave();
        }
        // After, just spawn in a new wave once all enemies are dead.
        else if (currentWave > 1 && timer.actsPassed() % ENEMY_ALIVE_CHECK_PERIOD == 0 && getObjects(Enemy.class).size() == 0) {
            spawnNextWave();
        }
    }
    private void spawnNextWave() {
        if (currentWave-1 >= spawnWaves.size()) { // The player has beat all of the enemy waves, and should move to the next room.
            if (!portalSpawned) { // If a portal tile did not exist yet, spawn one in.
                Cell randomCell = board.getRandomSpawnableCell();
                randomCell.setTile(new PortalTile());
                portalSpawned = true;
            }
            return;
        }
        // Spawn the next wave, since we haven't finished all the enemy waves.
        alert("SPAWNING WAVE " + currentWave, Color.RED);
        List<String> nextWave = spawnWaves.get(currentWave-1);
        for (String id : nextWave) { // Add all the enemies from that wave into the world.
            board.addEntity(Enemy.getInstanceFromID(id), board.getRandomSpawnableCell());
        }
        currentWave++;
    }
    private List<List<String>> generateSpawnWaves(int level) {
        List<List<String>> temp = new ArrayList<List<String>>();
        
        // Caclculate the number of waves I want to spawn in.
        int numWaves = level/3 + 1;
        // And the max and min levels.
        int minEnemies = 3 + Greenfoot.getRandomNumber(level/2 + 1);
        int maxEnemies = minEnemies + Greenfoot.getRandomNumber(level/3 + 2);
        
        for (int wave = 0; wave < numWaves; wave++) { // For each wave in this level...
            int waveEnemies = Utility.randomIntInRange(minEnemies, maxEnemies); // randomly choose the num of enemies in this wave.
            List<String> thisWave = new ArrayList<String>();
            for (int enemy = 0; enemy < waveEnemies; enemy++) { // Populate the wave.
                thisWave.add(Enemy.getRandomEnemyID());
            }
            temp.add(thisWave); // Add this wave to the spawnWaves.
        }
        
        
        return temp;
    }
    /**
     * Initializes the arraylist that holds all the possible room types that can exist
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
     * Returns a random board from the room types that exissts
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
}
