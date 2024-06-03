import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
