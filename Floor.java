import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>Floors are Tiles that are well, floors.</p>
 * 
 * <p>Generally walkable.</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public abstract class Floor extends Tile
{
    /**
     * Creates a floor of the given img
     * @param img the img to use
     */
    public Floor(GreenfootImage img) {
        super(true, img);
    }
    /**
     * Creates a floor from the given color
     * @param c the color to use
     */
    public Floor(Color c) {
        super(true, c);
    }
    public void act(){
        checkTouchTile();
    }
    /**
     * Abstract method for checking collision when entities walk on tiles and doing an appropiate effect
     */
    protected abstract void checkTouchTile();
}
