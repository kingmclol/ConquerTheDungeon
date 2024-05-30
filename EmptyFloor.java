import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Empty here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EmptyFloor extends Floor
{
    /**
     * Act - do whatever the Empty wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public EmptyFloor(){
        super(Color.GRAY);
    }
    public void act()
    {
        // Add your action code here.
    }
    public String getID() {
        return "f";
    }
}
