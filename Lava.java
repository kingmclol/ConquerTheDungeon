import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Lava here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Entity){
                lavaSound.play();
                ((Entity) owner).damage(Utility.randomIntInRange(5, 15));
            }
        }
    }
    protected void doEffect(Actor a){
        ((Entity) a).damage(Utility.randomIntInRange(5, 15));
    }
    public String getID(){
        return "lv";
    }
}
