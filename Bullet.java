import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.List;
/**
 * Bullet is a type of projectile that extends from Projectile
 * 
 * @author Osmond Lin
 * @version 2024-06-12
 */
public class Bullet extends Projectile
{
    private GreenfootImage bulletImage;
    int targetX;
    int targetY;
    
    /**
     * Bullet constructor that uses superclass constructor with additional parameters.
     * 
     * @aimX    The x-component of where the bullet is going towards
     * @aimY    The y-component of where the bullet is going towards
     */
    public Bullet(int spd, int dmg, Actor own, int aimX, int aimY){
        super(spd, dmg, own);
        targetX = aimX;
        targetY = aimY;
        bulletImage = new GreenfootImage("bullet.png");
        int newWidth = bulletImage.getWidth() * 3; 
        int newHeight = bulletImage.getHeight() * 3; 
        bulletImage.scale(newWidth, newHeight);
        setImage(bulletImage);
        
    }
    
    /**
     * Bullet constructor that uses superclass constructor.
     */
    public Bullet(int spd, int dmg, Actor own){
        super(spd, dmg, own);
        bulletImage = new GreenfootImage("bullet.png");
        int newWidth = bulletImage.getWidth() * 3; 
        int newHeight = bulletImage.getHeight() * 3; 
        bulletImage.scale(newWidth, newHeight);
        setImage(bulletImage);
        
    }
    
    public void act()
    {
        super.act();
    }
    
    public void addedToWorld(World world) {
        turnTowards(targetX, targetY);
    }
}
