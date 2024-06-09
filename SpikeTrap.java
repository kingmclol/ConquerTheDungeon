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
    private static GreenfootImage img = new GreenfootImage("spiketrap1.png");
    private static GreenfootImage img2 = new GreenfootImage("spiketrap2.png");
    private int activeCounter; 
    private int acts;
    private int period;
    public SpikeTrap() {
        super(img);
        activeCounter = 0;
        period = Utility.randomIntInRange(120, 240);
        acts = Greenfoot.getRandomNumber(period);
    }
    public void act()
    {
        if(++acts % period == 0 && activeCounter == 0) {
            activeCounter = Utility.randomIntInRange(period/4, period/2);
            setImage(img2);
        }
        if(activeCounter == 0){
            setImage(img);
        }
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
