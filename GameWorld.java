import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SuperWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameWorld extends SuperWorld
{

    /**
     * Constructor for objects of class SuperWorld.
     * 
     */
    public static final boolean SHOW_LOGS = true;    
    protected Board board;
    protected SuperStatBar playerBar;
    public GameWorld()
    { 
        super(); 

        setPaintOrder(Cursor.class, Segment.class, Box.class, SuperTextBox.class, TextBox.class,  Picture.class, UI.class, Cell.class, SuperStatBar.class, Projectile.class, Explosion.class, Entity.class, ShopItem.class, Aura.class, Drop.class, CellEffect.class, Tile.class);
        StatsUI ui = new StatsUI(GameData.getPlayer());
        addObject(ui, 1024+(1200-1024)/2, getHeight()/2);
        //GameData.importData();

        Tile.initializeTileDatabase();
        Room.initializeRoomDatabase();
        Enemy.initializeEnemyDatabase();
        Cursor cursor = new Cursor();
        addObject(cursor, getWidth() / 2, getHeight() / 2);
    }
    public void act() {
        super.act();
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
        alert(str, c, 100);
    }
    /**
     * Displays some text alert on the screen at your chosen y-level, centered at the board.
     * @param str The text to display
     * @param c the text color.
     * @param yLevel the y position in pixels it should appear.
     */
    public void alert(String str, Color c, int yLevel) {
        TextBox info = new TextBox(str, 36, c, null, 2, 255);
        addObject(info, (board.width()*Cell.SIZE)/2, yLevel);
        info.fadeOut();
    }
    /**
     * adds some colllison boxes around the playable area.
     */
    protected void addBorderBoxes() {
        int boardWidth = board.width() * Cell.SIZE;
        int boardHeight = board.height() * Cell.SIZE;
        int height = getHeight();
        int width = getWidth();
        // Add a collision box that blocks the RIGHT edge of the playable zone.
        CollisionBox rightBox = new CollisionBox(width-boardWidth, height, Box.SHOW_BOXES);
        addObject(rightBox, boardWidth + (width - boardWidth)/2, height/2);
        // now, to the left (beyond the visible area.
        CollisionBox leftBox = new CollisionBox(Cell.SIZE, height, Box.SHOW_BOXES);
        addObject(leftBox, -Cell.SIZE/2, height/2); 
        // Top now, with 2 extra cells just in case (to makae a rectangle with filled corners
        CollisionBox topBox = new CollisionBox(boardWidth + 2*Cell.SIZE, Cell.SIZE, Box.SHOW_BOXES);
        addObject(topBox, boardWidth/2, -Cell.SIZE/2);
        // Finally, bottom!
        CollisionBox bottomBox = new CollisionBox(boardWidth + 2*Cell.SIZE, Cell.SIZE, Box.SHOW_BOXES);
        addObject(bottomBox, boardWidth/2, height+Cell.SIZE/2);
    }
    
    /**
     * Goes to the next level, incrementing the level by 1.
     */
    public static void goToNextLevel() {
         // going to next level now
        //GameData.exportData();
        GameData.incrementLevel();
        Greenfoot.setWorld(new Room(GameData.getLevel()));
    }
    /**
     * Starts the game, depending on whether it is from a new game or not
     */
    public static void startGame(boolean savedGame) {
        if (!savedGame) GameData.incrementLevel();
        Greenfoot.setWorld(new Room(GameData.getLevel()));
    }
}
