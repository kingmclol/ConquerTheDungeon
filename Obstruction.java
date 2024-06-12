import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>An obstruction is a type of Tile in that it is generally an obstruction (lol) such as a wall.</p>
 * 
 * <p>The main thing about an obstruction is that it has a collisionbox to push entities away.
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public abstract class Obstruction extends Tile
{
    /**
     * Act - do whatever the Obstruction wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected CollisionBox AABB;
    /**
     * Creates an obstruction of the given color
     * @param c The color to use
     */
    public Obstruction(Color c){
        super(false ,c);
        AABB = new CollisionBox(Cell.SIZE, Cell.SIZE, Box.SHOW_BOXES, this);
    }
    /**
     * Creates an obstruction of the given image
     * @param img the image to use
     */
    public Obstruction(GreenfootImage img) {
        super(false, img);
        AABB = new CollisionBox(Cell.SIZE, Cell.SIZE, Box.SHOW_BOXES, this);
    }
    public void addedToWorld(World w) {
        w.addObject(AABB, 0, 0);
    }
    
}
