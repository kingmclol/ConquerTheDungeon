import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>A generic floor tile that is meant to be stepped on.</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public class EmptyFloor extends Floor
{
    private static GreenfootImage img = new GreenfootImage("floor.png");
    public EmptyFloor(){
        super(img);
    }
    public void act()
    {
    }
    protected void checkTouchTile(){
    }
    public String getID() {
        return "f";
    }
}
