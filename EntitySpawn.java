import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>An EntitySpawn is a CellEffect that will spawn in a Entity with a cool animation.</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public class EntitySpawn extends CellEffect
{
    private static GreenfootImage[] animation = Utility.createAnimation("CellTarget/cellTarget", ".png", 20);
    private int index;
    private Timer timer;
    private static final int spawnFrame = 14;
    private Entity entity;
    /**
     * Creates an entity spawn that will spawn in the given Entity.
     * @param e The entity to spawn in.
     */
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
