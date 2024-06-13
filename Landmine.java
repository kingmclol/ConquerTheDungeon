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
    private GreenfootSound explosion = new GreenfootSound("explosion.mp3"); 
    public static GreenfootImage img = new GreenfootImage("landmine.png");
    private HiddenBox hiddenBox;
    private boolean exploded;
    public Landmine() {
        super(img);  
        hiddenBox = new HiddenBox(40, 40, Box.SHOW_BOXES, this);
        explosion.setVolume(50);
    }
    public void addedToWorld(World w) {
        w.addObject(hiddenBox, getX(), getY());

    }
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
            ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) hiddenBox.getIntersectingBoxes(CollisionBox.class);
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
