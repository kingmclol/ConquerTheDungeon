import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Slows down the speed of any entities that touch the tile.
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class SlowTrap extends Floor
{

    private GreenfootSound slowTrapSound = new GreenfootSound("slowTrapSound.mp3"); 
    private static GreenfootImage img = new GreenfootImage("slowtrap.png");
    public SlowTrap() {
        super(img);
        slowTrapSound.setVolume(100); 
    }
    public void act()
    {
        checkTouchTile();
    }

    protected void checkTouchTile(){
        // Checks if the tile is in contact with any collision box
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Entity){
                slowTrapSound.play(); 
                // sets the speed to 60% of its original speed
                ((Entity) owner).setSpeed(0.6);
            }
        }
    }

    public String getID() {
        return "slw";
    }
}
