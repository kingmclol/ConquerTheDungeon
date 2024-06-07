import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Landmine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Landmine extends Floor
{

    public static GreenfootImage img = new GreenfootImage("landmine.png");
    private HiddenBox hiddenBox;
    private boolean exploded;
    public Landmine() {
        super(img);  
        

    }
    private void explode(){
        
        getWorld().addObject(new Explosion(1.0), getX(), getY());
        replaceMe(getInstanceFromID("f"));
  
    }
    public void act()
    {
        checkTouchTile();
    }

    protected void checkTouchTile(){
        if(!exploded){
            ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
            //System.out.println(boxes);
            for(Box box : boxes){
                Actor owner = box.getOwner();
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
