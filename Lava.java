import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Lava repeatedly does damage to an entity when the entity is in contact with it
 * 
 * @author Neelan Thurairajah
 * @version June 2024
 */
public class Lava extends Floor
{
    /**
     * Act - do whatever the Lava wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootSound lavaSound = new GreenfootSound("lavaSound.mp3");
    public static GreenfootImage img = new GreenfootImage("lava.jpg");
    public Lava() {
        super(img);
        walkable = false;
    }
    public void act()
    {
        checkTouchTile();
    }
    protected void checkTouchTile(){
        // Check if the lava tile is in contact with any collision box
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Entity){
                lavaSound.play();
                // damages entity
                ((Entity) owner).damage(Utility.randomIntInRange(5, 15));
            }
        }
    }

    public String getID(){
        return "lv";
    }
}
