import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Landmine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Landmine extends Floor
{

    public static GreenfootImage img = new GreenfootImage("landmine.jpg");
    private HiddenBox hiddenBox;
    public Landmine() {
        super(img);        
    }
    private void explode(){
    }
    public void act()
    {

    }
    public String getID() {
        return "lm";
    }
}
