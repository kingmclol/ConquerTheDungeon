import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Explosions are an area of effect that damages entities and enters them into a flung state.
 * <p>During a flung state, entities are knocked back and cannot control their movement for a short period of time. </p>
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class Explosion extends SuperSmoothMover
{
    private GreenfootImage[] animate = new GreenfootImage[10];
    private int frame = 0, acts = 0;
    private double size;
    /**
     * Creates an explosion with a size factor 
     * @param size  Size multiplier for the explosion, 1.0 being base size of 80 pixels
     */
    public Explosion(double size){
        this.size = size;
        for(int i = 0; i<animate.length; i++)
        {
            //animate[i] = new GreenfootImage("Star/star" + (i+1) + ".png");
            animate[i] = new GreenfootImage("Explosion/explode" + (i + 3) + ".png");
            animate[i].scale((int) ((double)animate[i].getWidth() * size), (int)((double)animate[i].getHeight()*size));
        }
    }
    public void addedToWorld(World w){
        // Get all collison boxes in a range of 80 pixels mulitplied by a size factor
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getObjectsInRange((int) (80.0 * size), CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Entity){
                // Sets the entity to be flung
                ((Entity) owner).setFlungState(true);
                // damages the entitiy
                ((Entity) owner).damage(Utility.randomIntInRange(10, 20));
            }
        }
    }
    public void act()
    {
        
        if(frame == (animate.length -1)){
            getWorld().removeObject(this);
        }
        animate();
        acts++;
    }
    public void animate()
    {   
        
        if(acts%7 == 0)
        {
            frame = (frame+1) % (animate.length);
        }
        setImage(animate[frame]);
    }
}
