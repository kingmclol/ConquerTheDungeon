import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A spike trap periodically triggers spikes that damage any entities that touch the tile.
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class SpikeTrap extends Floor
{
    private GreenfootSound spikeTrapSound = new GreenfootSound("spikeTrapSound.mp3");
    private static GreenfootImage img = new GreenfootImage("spiketrap1.png");
    private static GreenfootImage img2 = new GreenfootImage("spiketrap2.png");
    private int activeCounter; 
    private int acts;
    private int period;
    /**
     * Creates a spike trap with a random interval and period for spike activation
     */
    public SpikeTrap() {
        super(img);
        activeCounter = 0;
        period = Utility.randomIntInRange(120, 240);
        acts = Greenfoot.getRandomNumber(period);
        spikeTrapSound.setVolume(50);
    }
    public void act()
    {
        if(++acts % period == 0 && activeCounter == 0) {
            // every cycle, set a random activationlength
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
            // get all touching collision boxes
            for(Box box : boxes){
                Actor owner = box.getOwner();
                if(owner instanceof Entity){
                    spikeTrapSound.play();
                    //damage the entity
                    ((Entity) owner).damage(Utility.randomIntInRange(2, 10));
                }
            }
            // decrement the active timer
            activeCounter--;
        }
        

        
    }
}
