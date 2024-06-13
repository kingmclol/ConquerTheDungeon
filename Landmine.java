import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A landmine that explodes when an entity walks on it, dealing damage in an area of effect.
 * 
 * @author Neelan Thurairajah
 * @version June 2024
 */
public class Landmine extends Floor
{
    private GreenfootSound explosion = new GreenfootSound("explosion.mp3"); 
    public static GreenfootImage img = new GreenfootImage("landmine.png");
    private HiddenBox hiddenBox;
    private boolean exploded;
    /**
     * Creates landmine with a hidden box that is used for collision detection for entities
     */
    public Landmine() {
        super(img);  
        hiddenBox = new HiddenBox(40, 40, Box.SHOW_BOXES, this);
        explosion.setVolume(50);
    }
    public void addedToWorld(World w) {
        w.addObject(hiddenBox, getX(), getY());

    }
    /**
     * Adds an explosion object and replaces the landmine with a regular floor tile
     */
    private void explode(){
        explosion.play();
        getWorld().addObject(new Explosion(1.0), getX(), getY());
        replaceMe(getInstanceFromID("f"));
    }
    public void act()
    {
        checkTouchTile();
    }
    
    protected void checkTouchTile(){
        if(!exploded){
            // gather all collidable objects in contact with the landmine
            ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) hiddenBox.getIntersectingBoxes(CollisionBox.class);
            for(Box box : boxes){
                //gets the owner of the collision box
                Actor owner = box.getOwner();
                // Checks if it is an entity
                if(owner instanceof Entity){
                    exploded = true;
                    explode();
                    break;
                }
            }
        }
 
    }
    
    public String getID() {
        return "lm";
    }
}
