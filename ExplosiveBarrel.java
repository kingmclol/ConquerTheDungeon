import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ExplosiveBarrel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExplosiveBarrel extends Destroyable
{
    /**
     * Act - do whatever the ExplosiveBarrel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private static GreenfootImage img = new GreenfootImage("explosivebarrel.png");
    public ExplosiveBarrel() {
        super(img, 15);
    }
    public void act()
    {
        // Add your action code here.
    }
    public void onDestroy() {
        getWorld().addObject(new Explosion(1.8), getX(), getY());
        replaceMe(new EmptyFloor());
    }
    public String getID() {
        return "eb";
    }
}
