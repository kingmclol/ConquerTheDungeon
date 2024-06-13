import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * <p>The CombatRoom is the room where the Player fights the enemies, which come in various waves.</p>
 * 
 * <p>Upon killing all enemies, another wave may spawn, or a portal that leads to the next room may spawn.</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public class CombatRoom extends Room
{
    private List<List<String>> spawnWaves;
    private static final int FIRST_WAVE_WAIT = 180;
    private int currentWave;
    private static final int ENEMY_ALIVE_CHECK_PERIOD = 60; // Will check if all enemies are dead every this amount of acts.
    private boolean portalSpawned;
    /**
     * Creates a CombatRoom of the given level.
     * @param level The level of this room
     */
    public CombatRoom(int level)
    {
        super(level, Room.getRandomBoard());
        
        // Spawn waves generation
        spawnWaves = generateSpawnWaves(currentLevel);
        currentWave = 1;
        
        portalSpawned = false;
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
    /**
     * Spawn the next wave onto the world.
     */
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
    /**
     * Returns the spawn waves of  this combat room as a List of lists.
     */
    private List<List<String>> generateSpawnWaves(int level) {
        List<List<String>> temp = new ArrayList<List<String>>();
        
        // Caclculate the number of waves I want to spawn in.
        int numWaves = level/5 + 1;
        // And the max and min levels.
        int minEnemies = 3 + Greenfoot.getRandomNumber(level/4 + 1);
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
}
