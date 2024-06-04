import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class SpeedBoost here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpeedBoost extends SpecialTiles
{
    /**
     * Act - do whatever the SpeedBoost wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */


    public SpeedBoost(GreenfootImage img) {
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
                //doEffect(owner);
            }
        }
    }
 
    
    public String getID() {
        return "spb";
    }
}
