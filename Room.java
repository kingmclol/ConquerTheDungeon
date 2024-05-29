import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Room here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Room extends SuperWorld
{

    /**
     * Constructor for objects of class Room.
     * 
     */
    Board board;
    public Room(Board b)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        board = b;
        
    }
    public Board getBoard() {
        return board;
    }
}
