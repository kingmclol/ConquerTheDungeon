import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SuperWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameWorld extends World
{

    /**
     * Constructor for objects of class SuperWorld.
     * 
     */
    public static final boolean SHOW_LOGS = true;
    protected Board board;
    public GameWorld()
    { 
        super(1200, 768, 1); 
        setPaintOrder(Cursor.class, CollisionBox.class, TextBox.class, Picture.class, Cell.class, SuperStatBar.class, Entity.class, Aura.class, PowerUp.class, Heal.class, CellEffect.class, Tile.class);
        Mouse.setWorld(this);
        Tile.initializeTileDatabase();
        Room.initializeRoomDatabase();
        Cursor cursor = new Cursor();
        addObject(cursor, getWidth() / 2, getHeight() / 2);
    }
    public void act() {
        Timer.tick();
        Mouse.getMouse();
    }
    public Board getBoard() {
        return board;
    }
}
