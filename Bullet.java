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
    
    public Bullet(int x, int y, int spd, int dmg, Actor own){
        super(x, y, spd, dmg, own);
    }
    public void act()
    {
        // Add your action code here.
    }
}
