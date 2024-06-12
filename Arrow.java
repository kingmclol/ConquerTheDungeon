import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Arrow is a type of projectile that extends the Projectile superclass
 * 
 * @author Arrow
 * @version 2024-06-12
 */
public class Arrow extends Projectile
{
    private GreenfootImage arrowImage;
    private Actor target;
    
    public Arrow(int spd, int dmg, Actor own, Actor targ){
        super(spd, dmg, own);
        target = targ;
        arrowImage = new GreenfootImage("arrow.png");
        int newWidth = arrowImage.getWidth() / 20; 
        int newHeight = arrowImage.getHeight() / 20; 
        arrowImage.scale(newWidth, newHeight);
        setImage(arrowImage);
    }
    
    public void act()
    {
        super.act();
    }
    
    public void addedToWorld(World world) {
        turnTowards(target.getX(), target.getY());
    }
}
