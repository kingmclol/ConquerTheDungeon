import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RoomEditor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RoomEditor extends SuperWorld
{

    /**
     * Constructor for objects of class RoomEditor.
     * 
     */
    private Board b;
    public RoomEditor()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        b = new Board(16, 12);
        addObject(b, 0,0);
    }
}
