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
    private int count;
    private int actCount;
    private static GreenfootImage img = new GreenfootImage("portal1.png");
    public PortalTile()
    {
        super(img);
        count = 1;
        actCount = 0;
    }
    public void act() {
        actCount++; 
        if (actCount % 10 == 0) {
            count++;
            GreenfootImage img = new GreenfootImage("portal" + count + ".png");
            setImage(img);
            if (count == 4) {
                count = 0;
            }
        }
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
