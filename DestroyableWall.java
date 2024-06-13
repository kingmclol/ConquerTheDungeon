import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A wall that can be destroyed.
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class DestroyableWall extends Destroyable
{

    private GreenfootSound breakWallSound = new GreenfootSound("wallSound.mp3"); 
    private static GreenfootImage img = new GreenfootImage("destroyablewall.jpg");
    public DestroyableWall() {
        super(img, 30);
        breakWallSound.setVolume(35);
    }
    public void act()
    {
        // Add your action code here.
    }
    public void onDestroy() {
        breakWallSound.play(); 
        replaceMe(new EmptyFloor());
    }
    public String getID() {
        return "wd";
    }
}
