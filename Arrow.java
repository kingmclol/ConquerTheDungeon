import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arrow extends Projectile
{
    private GreenfootImage arrowImage = new GreenfootImage("arrow.png");
    public Arrow(int spd, int dmg, Actor own){
        super(spd, dmg, own);
    }
    
    public void act()
    {
        super.act();
    }
}
