import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EntitySpawn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EntitySpawn extends CellEffect
{
    /**
     * Act - do whatever the EntitySpawn wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private static GreenfootImage[] animation = Utility.createAnimation("CellTarget/cellTarget", ".png", 20);
    private int index;
    private Timer timer;
    private static final int spawnFrame = 14;
    private Entity entity;
    public EntitySpawn(Entity e) {
        timer = new Timer();
        index = 0;
        entity = e;
    }
    public void act()
    {
        if (timer.actsPassed() % 3 == 0) {
            if (index == animation.length) { // Done the animation
                cell.removeEffect(this);
                return;
            }
            setImage(animation[index++]);
            if (index == spawnFrame) {
                getWorld().addObject(entity, getX(), getY()-20);
            }
        }
    }
}
