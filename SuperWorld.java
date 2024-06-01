import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SuperWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SuperWorld extends World
{

    /**
     * Constructor for objects of class SuperWorld.
     * 
     */
    public static final boolean SHOW_LOGS = true;
    public SuperWorld()
    { 
        super(1200, 768, 1); 
        setPaintOrder(TextBox.class, Picture.class, Cell.class, Entity.class, CellEffect.class, Tile.class);
        Mouse.setWorld(this);
        Tile.initializeTileDatabase();
        Room.initializeRoomDatabase();
        Cursor cursor = new Cursor();
        addObject(cursor, getWidth() / 2, getHeight() / 2);
    }
    public void act() {
        Timer.tick();
        Mouse.getMouse();
    }
}
