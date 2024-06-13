import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An explosive barrel is a destroyable object that creates a large explosion upon destruction.
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class ExplosiveBarrel extends Destroyable
{
 
    private GreenfootSound explosion = new GreenfootSound("explosion.mp3"); 
    private static GreenfootImage img = new GreenfootImage("explosivebarrel.png");
    public ExplosiveBarrel() {
        super(img, 15);
        explosion.setVolume(40);
    }

    public void onDestroy() {
        explosion.play();
        // Create a large explosion
        getWorld().addObject(new Explosion(1.8), getX(), getY());
        replaceMe(new EmptyFloor());
    }
    public String getID() {
        return "eb";
    }
}
