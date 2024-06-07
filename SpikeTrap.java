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
    private int activeCounter; 
    private int acts;
    public SpikeTrap() {
           
        super(new Color(252, 13, 121));
        activeCounter = 0;
    }
    public void act()
    {
        if(acts % 130 == 0 && activeCounter == 0) {
            activeCounter = Utility.randomIntInRange(30, 70);
            setImage(img2);
        }
        if(activeCounter == 0){
            setImage(img);
        }
        acts++;
        checkTouchTile();
    }
    public String getID() {
        return "spk";
    }
    protected void checkTouchTile(){
        if(activeCounter > 0){
            ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);

            for(Box box : boxes){
                Actor owner = box.getOwner();
                if(owner instanceof Entity){
                    ((Entity) owner).damage(Utility.randomIntInRange(2, 10));
                }
            }
            activeCounter--;
        }
        

        
    }
}
