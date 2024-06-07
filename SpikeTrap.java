import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class SpikeTrap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpikeTrap extends Floor
{
    private GreenfootImage img = new GreenfootImage("spiketrap1.png");
    private GreenfootImage img2 = new GreenfootImage("spiketrap2.png");
    public SpikeTrap() {
       
        super(new Color(252, 13, 121));
    }
    public void act()
    {
        checkTouchTile();
    }
    public String getID() {
        return "spk";
    }
    protected void checkTouchTile(){
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        if(boxes.size() == 0){
            setImage(img);
        }
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Entity){
                setImage(img2);
                ((Entity) owner).damage(Utility.randomIntInRange(2, 10));
            }
        }
        
    }
}
