import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class SpeedBoost here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpeedBoost extends Floor
{
    /**
     * Act - do whatever the SpeedBoost wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public static GreenfootImage img = new GreenfootImage("speedBoost.png");
    public SpeedBoost() {
        super(img);
    }

    public void act()
    {
        checkTouchTile();
    }

    protected void checkTouchTile(){
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Entity){
                ((Entity) owner).setSpeed(120);
            }
        }
    }

    public String getID() {
        return "spb";
    }
}
