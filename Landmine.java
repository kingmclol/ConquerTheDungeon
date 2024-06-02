import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Landmine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Landmine extends Floor
{
    /**
     * Act - do whatever the Landmine wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static GreenfootImage img = new GreenfootImage("landmine.jpg");
    public Landmine() {
        super(img);
    }
    public void act()
    {
        // Add your action code here.
    }
    public String getID() {
        return "lm";
    }
}
