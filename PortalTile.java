import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * <p>A portal tile teleports the player to the next level in the game when touching it.
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class PortalTile extends Floor
{

    private int count;
    private int actCount;
    private static GreenfootImage img = new GreenfootImage("portal1.png");
    /**
     * Creates a portal tile with act counters for animation.
     */
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
        super.act();
    }
    protected void checkTouchTile(){
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        // Gets all collision boxes in contact with the tile
        for(Box box : boxes){
            Actor owner = box.getOwner();
            // if the box's owner is a player, progress to the next level
            if(owner instanceof Player){
                GameWorld.goToNextLevel();
            }
        }
    }
    public String getID() {
        return "ptl";
    }
}
