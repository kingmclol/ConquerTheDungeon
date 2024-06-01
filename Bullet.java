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
    private GreenfootImage bulletImage;
    int targetX;
    int targetY;
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
    
    public void act()
    {
        super.act();
    }
    
    public void addedToWorld(World world) {
        turnTowards(targetX, targetY);
    }
}
