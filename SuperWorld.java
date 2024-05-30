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
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        Mouse.setWorld(this);
        Cursor cursor = new Cursor();
        addObject(cursor, getWidth() / 2, getHeight() / 2);
    }
    public void act() {
        Timer.tick();
    }
}
