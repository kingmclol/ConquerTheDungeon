import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class CollisionBox extends Actor
{
    // Instance variables 
    private int height;
    private int width;
    private int yOffset;
    private Actor owner;

    public CollisionBox (int width, int height, Actor owner, int yOffset) {
        this.width = width;
        this.height = height;
        this.owner = owner;
        this.yOffset = yOffset;
        GreenfootImage box = new GreenfootImage(width, height);
        setImage(box);
        //makeVisible();
    }

    public void act() {
        if (owner.getWorld() == null) { 
            getWorld().removeObject(this);
            return;
        }
        setLocation(owner.getX(), owner.getY()+yOffset);
    }

    /**
     * Method to make the collision boxes visible
     */
    public void makeVisible() {
        getImage().setColor(Color.RED);
        getImage().fill();
    }

    /**
     * Returns if the collision box is intersecting another actor
     */
    public boolean isIntersecting(Class c) {
        return isTouching(c);
    }
}
