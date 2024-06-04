import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Explosion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Explosion extends SuperSmoothMover
{
    private GreenfootImage[] animate = new GreenfootImage[10];
    private int frame = 0, acts = 0;
    public Explosion(){
        for(int i = 0; i<animate.length; i++)
        {
            //animate[i] = new GreenfootImage("Star/star" + (i+1) + ".png");
            animate[i] = new GreenfootImage("Explosion/explode" + (i + 3) + ".png");
        }
    }
    public void addedToWorld(World w){
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getObjectsInRange(80, CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Entity){
                ((Entity) owner).setFlungState(true);
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
