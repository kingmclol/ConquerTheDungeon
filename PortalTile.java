import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PortalTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PortalTile extends Floor
{
    /**
     * Act - do whatever the PortalTile wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public PortalTile()
    {
        super(Color.GREEN);
    }
    protected void checkTouchTile(){
        return;
    }
    public String getID() {
        return "ptl";
    }
}
