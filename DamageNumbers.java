import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DamageNumber here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DamageNumbers extends UI
{
    private int frames = 60, transparency = 255; // 1 second
    private GreenfootImage image;
    /**
     * Act - do whatever the DamageNumber wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public DamageNumbers(int dmg)
    {
        //Changes Int to String, 20 is for font, Red text and makes bg transparent - new Color(0,0,0,0)
        image = new GreenfootImage(Integer.toString(dmg), 20, Color.RED, new Color(0,0,0,0));
        setImage(image);
    }
    public void act()
    {
        // Add your action code here.
        frames--;
        transparency--;
        image.setTransparency(transparency);
        this.setLocation(getX(), (int)((double)getY()-0.5));
        if(frames == 0)
        {
            getWorld().removeObject(this);
        }
    }
}
