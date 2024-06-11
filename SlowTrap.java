import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class SlowTrap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SlowTrap extends Floor
{
    /**
     * Act - do whatever the SlowTrap wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public SlowTrap() {
        super(new Color(98, 100, 0));
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
                ((Entity) owner).setSpeed(0.6);
            }
        }
    }

    public String getID() {
        return "slw";
    }
}
