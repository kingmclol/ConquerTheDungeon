import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Goblin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Goblin extends Enemy
{
    //Link to Art: https://opengameart.org/content/lpc-goblin
    /**
     * Act - do whatever the Goblin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Goblin()
    {
        collisionBox = new CollisionBox(30, 30, Box.SHOW_BOXES, this);
    }
}
