import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.List;
/**
 * Write a description of class Projectile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectile extends SuperSmoothMover
{
    private int spawnOffset = 10;
    
    private int positionX;
    private int positionY;
    private int rotation;
    
    private int speed;
    private int damage;
    private Actor owner;
    
    public Projectile(int x, int y, int spd, int dmg, Actor own){
        positionX = x;
        positionY = y;
        speed = spd;
        damage = dmg;
        owner = own;
    }
    
    public void act()
    {
        List<Actor> actors = getIntersectingObjects(Actor.class);
        
        for(Actor a : actors){
            if(a instanceof Player) {
                ((Player)a).takeDamage(10);
                getWorld().removeObject(this);
            }else if(a instanceof Enemy){
                ((Enemy)a).takeDamage(10);
                getWorld().removeObject(this);
            }
        }
    }
    
    public void spawn(World world){
        
    }
}
