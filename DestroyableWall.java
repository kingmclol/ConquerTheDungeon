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
    private static GreenfootImage img = new GreenfootImage("destroyablewall.jpg");
    public DestroyableWall() {
        super(img, 30);
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
