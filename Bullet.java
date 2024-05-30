import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.List;
/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends Projectile
{
    private GreenfootImage bulletImage = new GreenfootImage("bullet.png");
    int targetX;
    int targetY;
    public Bullet(int spd, int dmg, Actor own, int aimX, int aimY){
        super(spd, dmg, own);
        targetX = aimX;
        targetY = aimY;
        GreenfootImage image = new GreenfootImage("bullet.png");
        int newWidth = image.getWidth() * 3; 
        int newHeight = image.getHeight() * 3; 
        image.scale(newWidth, newHeight);
        setImage(image);
        
    }
    
    public void act()
    {
        super.act();
    }
    
    public void addedToWorld(World world) {
        turnTowards(targetX, targetY);
    }
}
