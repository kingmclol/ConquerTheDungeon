import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

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
    private static GreenfootImage img = new GreenfootImage("portal.png");
    public PortalTile()
    {
        super(img);
    }
    protected void checkTouchTile(){
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Player){
                GameWorld.goToNextLevel();
            }
        }
    }
    public String getID() {
        return "ptl";
    }
}
