import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShopRoom here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopRoom extends GameWorld
{

    /**
     * Constructor for objects of class ShopRoom.
     * 
     */
    private int currentLevel;
    private Timer timer;
    private static final int FIRST_WAVE_WAIT = 180;
    private int currentWave;
    private static final int ENEMY_ALIVE_CHECK_PERIOD = 60; // Will check if all enemies are dead every this amount of acts.
    private boolean portalSpawned;
    private static final String fallbackBuildString = "16~12~f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/w/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/w/w/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/";

    public ShopRoom()
    {    
        super();
        
        timer = new Timer();
        //board = new Board();
        addObject(board, 0, 0);


        board.addEntity(GameData.getPlayer(), board.getRandomSpawnableCell());
        portalSpawned = false;
        alert("SHOP", Color.WHITE, getHeight()-100);
        addBorderBoxes();
    }
}
