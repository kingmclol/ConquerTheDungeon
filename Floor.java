import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Floor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Floor extends Tile
{
    /**
     * Act - do whatever the Floor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Floor(GreenfootImage img) {
        super(true, img);
    }
    public Floor(Color c) {
        super(true, c);
    }
    public Floor(Animation a) {
        super(true, a);
    }
    public void act()
    {
        // Add your action code here.
    }
}
