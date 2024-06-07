import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealingEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealingEffect extends Heal
{
    private int currentXOffset, yOffset, frame = 0, acts = 0;
    private int[] xOffsets = new int[2];
    private double speed = 1.7;
    private Actor owner;
    private Player player;
    private boolean updatePositionFirst = false;
    private GreenfootImage[] animate = new GreenfootImage[3];
    /**
     * Act - do whatever the HealingEffect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public HealingEffect(Actor owner, int xOffset, int yOffset)
    {
        super(1);
        xOffsets[0] = xOffset;
        xOffsets[1] = xOffset -10;
        this.yOffset = yOffset;
        this.owner = owner;
        for(int i = 0; i<3; i++)
        {
            animate[i] = new GreenfootImage("Particles/heal" + (i+1) + ".png");
            animate[i].scale(animate[i].getWidth()/8, animate[i].getHeight()/8);
        }
        player = (Player)owner;
    }

    public void act()
    {
        // Add your action code here.
        if(!updatePositionFirst)
        {
            setLocation(owner.getX() + currentXOffset, getY() + yOffset);
            updatePositionFirst = true;
        }
        acts++;
        if(acts % 90 == 0)
        {
            player.heal(1);
        }
        animate();
        moveMe();
        if(acts >= 180 || owner.getWorld() == null)
        {
            getWorld().removeObject(this);
        }
    }

    public void animate()
    {
        if(acts % 30 == 0)
        {
            frame = (frame+1)%(3);
        }
        setImage(animate[frame]);
    }

    public void moveMe()
    {
        if(player.getFacing().equals("right"))
        {
             currentXOffset = xOffsets[1];
        }
        else
        {
            currentXOffset = xOffsets[0];
        }
        if(acts % 6 == 0)
        {
            setLocation(owner.getX() + currentXOffset, getY() - speed);
        }
        if((owner.getY() - this.getY()) >= 15 || (this.getY() - owner.getY()) >= 15)
        {
            setLocation(owner.getX() + currentXOffset, owner.getY() + yOffset);
        }
        if((owner.getX() - this.getX()) >= 8 || (this.getX() - owner.getX()) >= 8)
        {
            setLocation(owner.getX() + currentXOffset, owner.getY() + yOffset);
        }
    }
}
