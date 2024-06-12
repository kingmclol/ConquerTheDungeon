import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>The SuperWorld is simply the world that all worlds should extend from. It has important methods to ensure the
 * functionality of some other utility classes, such as Timer, Mouse, and Keyboard.</p>
 * 
 * <p>It is highly important that the act() of this is run for the same reason as above.</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public class SuperWorld extends World
{
    /**
     * Constructor for objects of class SuperWorld.
     */
    public SuperWorld()
    {    
        super(1200, 768, 1, false);  
        Mouse.setWorld(this);
        
        // Initialize the maps for getting instances
        Tile.initializeTileDatabase();
        Room.initializeRoomDatabase();
        Enemy.initializeEnemyDatabase();
    }
    public void act() {
        Timer.tick();
        Mouse.getMouse();
        Keyboard.update();
    }
    /**
     * Displays some text alert on the screen.
     * @param str The text to display
     * @param c the color of the text.
     */
    public void alert(String str, Color c) {
        alert(str, c, 100);
    }
    /**
     * Displays some text alert on the screen at your chosen y-level.
     * @param str The text to display
     * @param c the text color.
     * @param yLevel the y position in pixels it should appear.
     */
    public void alert(String str, Color c, int yLevel) {
        TextBox info = new TextBox(str, 36, c, null, 2, 255);
        addObject(info, getWidth()/2, yLevel);
        info.fadeOut();
    }
}
