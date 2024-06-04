import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Landmine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Landmine extends SpecialTiles
{

    private GreenfootImage[] animate = new GreenfootImage[12];
    private int frame = 0, acts = 0;
    public static GreenfootImage img = new GreenfootImage("landmine.png");
    private HiddenBox hiddenBox;
    private boolean exploded;
    public Landmine() {
        super(img);  
        

    }
    private void explode(){
        
        getWorld().addObject(new Explosion(), getX(), getY());
        replaceMe(getInstanceFromID("f"));
  
    }
    public void act()
    {
        acts++;
        checkTouchTile();
    }

    protected void checkTouchTile(){
        if(!exploded){
            ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
            System.out.println(boxes);
            for(Box box : boxes){
                Actor owner = box.getOwner();
                if(owner instanceof Entity){
                    
                    explode();
                }
            }
        }
 
    }
    
    public String getID() {
        return "lm";
    }
}
