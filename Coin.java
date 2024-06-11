import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Coin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coin extends Drop
{
    private GreenfootImage[] animate = new GreenfootImage[6];
    private int frame = 0, acts = 0;
    public Coin(){
        for(int i = 0; i<animate.length; i++)
        {
            //animate[i] = new GreenfootImage("Star/star" + (i+1) + ".png");
            animate[i] = new GreenfootImage("Coin/coin" + (i + 1) + ".png");
            animate[i].scale(35, 35);
        }
        setImage(animate[0]);
    }


    public void act()
    {
        
        animate();
        checkCollisionWithPlayer();
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
    public void checkCollisionWithPlayer() {
        ArrayList<CollisionBox> boxes = (ArrayList<CollisionBox>) getIntersectingObjects(CollisionBox.class);
        for(Box box : boxes){
            Actor owner = box.getOwner();
            if(owner instanceof Player){
                
                
                GameData.getPlayer().addCoin();
                getWorld().removeObject(this);
            }
        }
    }
}
