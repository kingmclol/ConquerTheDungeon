import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * <p> Coins are drops that are used to purchase items in the shop
 * 
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class Coin extends Drop
{
    private GreenfootSound getCoinSound = new GreenfootSound("coinSound.mp3");
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
                getCoinSound.play(); 
                GameData.getPlayer().addCoin();
                getWorld().removeObject(this);
            }
        }
    }
}
