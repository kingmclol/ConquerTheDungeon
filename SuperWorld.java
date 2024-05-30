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
    public SuperWorld()
    { 
        super(1024, 768, 1); 
        setPaintOrder(Picture.class, Cell.class, Entity.class, CellEffect.class, Tile.class);
        Mouse.setWorld(this);
        Tile.initializeTileDatabase();
        Room.initializeRoomDatabase();
    }
    public void act() {
        Timer.tick();
        Mouse.getMouse();
    }
}
