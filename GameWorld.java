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
        super(1200, 768, 1, false); 

        setPaintOrder(Cursor.class, Segment.class, Box.class, SuperTextBox.class, TextBox.class,  Picture.class, UI.class, Cell.class, SuperStatBar.class, Projectile.class, Explosion.class, Entity.class, Aura.class, Drop.class, CellEffect.class, Tile.class);
        StatsUI ui = new StatsUI();
        addObject(ui, 1024+(1200-1024)/2, getHeight()/2);
        Mouse.setWorld(this);
        Tile.initializeTileDatabase();
        Room.initializeRoomDatabase();
        Cursor cursor = new Cursor();
        addObject(cursor, getWidth() / 2, getHeight() / 2);
    }
    public void act() {
        Timer.tick();
        Mouse.getMouse();
        Keyboard.update();
    }
    public Board getBoard() {
        return board;
    }
    /**
     * Displays some text alert on the screen.
     * @param str The text to display
     * @param c the color of the text.
     */
    public void alert(String str, Color c) {
        TextBox info = new TextBox(str, 36, c, null, 2, 255);
        addObject(info, getWidth()/2, 100);
        info.fadeOut();
    }
}
