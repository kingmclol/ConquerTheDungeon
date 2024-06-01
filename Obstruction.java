import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Obstruction here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Obstruction extends Tile
{
    /**
     * Act - do whatever the Obstruction wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    protected CollisionBox AABB;
    public Obstruction(Color c){
        super(false ,c);
        AABB = new CollisionBox(Cell.SIZE, Cell.SIZE, Box.SHOW_BOXES, this);
    }
    public Obstruction(GreenfootImage img) {
        super(false, img);
    }
    public void addedToWorld(World w) {
        w.addObject(AABB, 0, 0);
    }
}
