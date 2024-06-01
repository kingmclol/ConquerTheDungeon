import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Desructible here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Barrel extends Destroyable
{
    /**
     * Act - do whatever the Desructible wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Barrel() {
        super(new Color(240, 140, 48), 10);
    }
    public void onDestroy() {
        getWorld().removeObject(this);
    }
    public String getID() {
        return "b";
    }
}
