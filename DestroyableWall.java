import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DestroyableWall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DestroyableWall extends Destroyable
{
    /**
     * Act - do whatever the DestroyableWall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public DestroyableWall() {
        super(new Color(89, 79, 62), 30);
    }
    public void act()
    {
        // Add your action code here.
    }
    public void onDestroy() {
        replaceMe(new EmptyFloor());
    }
    public String getID() {
        return "wd";
    }
}
