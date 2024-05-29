import greenfoot.*;
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tile extends Actor
{
    // instance variables - replace the example below with your own
    public static boolean DRAW_BORDERS = true;
    public static final int tileSize = 64;
    /**
     * Constructor for objects of class Tile
     */
    public Tile(Color c)
    {
        //GreenfootImage img = new GreenfootImage(GameWorld.tileSize, GameWorld.tileSize);
        GreenfootImage img = new GreenfootImage(tileSize, tileSize);
        img.setColor(c);
        img.fill();
        if (DRAW_BORDERS) {
            img.setColor(Color.BLACK);
            img.drawRect(0,0, tileSize, tileSize);
            //img.drawRect(0,0,GameWorld.tileSize, GameWorld.tileSize);
        }
        setImage(img);
    }
}
