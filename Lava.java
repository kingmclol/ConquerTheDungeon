import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Lava here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Lava extends Floor
{
    /**
     * Act - do whatever the Lava wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static GreenfootImage img = new GreenfootImage("lava.jpg");
    public Lava() {
        super(img);
    }
    public void act()
    {
        // Add your action code here.
    }
    public String getID(){
        return "lv";
    }
}
