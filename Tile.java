import greenfoot.*;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * <p>Tiles are those things that you see on the board (e.g. a lava tile, or a spike tile).</p>
 * 
 * <p>They can be walkable or not walkable depending on your choice, and they may have different behaviours.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Tile extends SuperActor
{
    private static final boolean DRAW_BORDERS = false;
    
    protected boolean walkable;
    private static HashMap<String, Class> tileDatabase;
    private Cell cell;
    /**
     * Creates a tile of a given colour and walkabiltiy.
     * @param walkable Whether this tile is walkable
     * @param c the color of the tile
     */
    public Tile(boolean walkable, Color c)
    {
        GreenfootImage img = new GreenfootImage(Cell.SIZE, Cell.SIZE);
        this.walkable = walkable;
        img.setColor(c);
        img.fill();
        setImage(img);
    }
    /**
     * Creates a tile of the given image and walkability.
     * @param walkable whether the tile is walkable
     * @param img the image to use, scaled to the correct dimensions
     */
    public Tile (boolean walkable, GreenfootImage img) {
        img.scale(Cell.SIZE, Cell.SIZE);
        this.walkable = walkable;
        setImage(img);
    }
    /**
     * Set the image of this tile to the given image
     * @param img the new image to use.
     */
    public void setImage(GreenfootImage img) {
        if (DRAW_BORDERS) {
            img.setColor(Color.BLACK);
            img.drawRect(0,0, Cell.SIZE, Cell.SIZE);
            //img.drawRect(0,0,GameWorld.tileSize, GameWorld.tileSize);
        }
        super.setImage(img);
    }
    /**
     * Return whether this tile is walkable
     */
    public boolean isWalkable() {
        return walkable;
    }
    /**
     * Gets the ID of the tile
     */
    public abstract String getID();
    /**
     * Returns an instance of a Tile given an ID.
     * @param id The id of the tile to get an instance of
     * @return The respective tile, of an EmptyFloor if something wrong occured.
     */
    public static Tile getInstanceFromID(String id) {
        if (tileDatabase == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: attempted to get tile when tileDatabase is null");
            return new EmptyFloor();
        }
        Class c = tileDatabase.get(id);
        if (c == null) { // Invalid id
            if (GameWorld.SHOW_LOGS) System.out.println("err: the tile ID \"" + id + "\" was invalid");
            return new EmptyFloor();
        }
        
        try
        {
            return (Tile) c.newInstance();
        }
        catch (IllegalAccessException iae) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: encountered IllegalAccessException from tile ID: " + id);
        }
        catch (InstantiationException ie)
        {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: encountered InstantiationException from tile ID: " + id);
        }
        return new EmptyFloor(); // Something really bad went wrong. ouch.
    }
    /**
     * Initialize the database of tiles. Must be run very early on.
     */
    public static void initializeTileDatabase() {
        if (tileDatabase != null) return;
        tileDatabase = new HashMap<String, Class>();
        tileDatabase.put("f", EmptyFloor.class);
        tileDatabase.put("w", Wall.class);
        tileDatabase.put("b", Barrel.class);
        tileDatabase.put("wd", DestroyableWall.class);
        tileDatabase.put("eb", ExplosiveBarrel.class);
        tileDatabase.put("spk", SpikeTrap.class);
        tileDatabase.put("spb", SpeedBoost.class);
        tileDatabase.put("lm", Landmine.class);
        tileDatabase.put("lv", Lava.class);
        tileDatabase.put("slw", SlowTrap.class);
        tileDatabase.put("ptl", PortalTile.class);
        tileDatabase.put("shp", ShopTile.class);
        if (GameWorld.SHOW_LOGS) System.out.println("info: loaded in tile database");
    }
    /**
     * Returns a string array of the tile--class pairs for display.
     */
    public static String[] getLegend() {
        if (tileDatabase == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: something tried to generate legend before database made");
            return new String[]{"Something wrong :("};
        }
        ArrayList<String> data = new ArrayList<String>();
        // For each entry in the map, run this function.
        tileDatabase.forEach((k,v) -> {
            // k as the id, v as "class ExplosiveBarrel" and stuff.
            data.add(getClassName(k) + " ID: " + k);
            data.add(""); // "newline moment"
        });
        return data.toArray(new String[1]); // Well, apparently you have to pass in an array for toArray() to return an array
        // of the same type as the parameter array (in this case, String[]
    }
    /**
     * Verify if the given ID exists in the tileDatabase.
     * @param key the ID to check.
     * @return whether the ID is valid
     */
    public static boolean verifyID(String key) {
        if (tileDatabase == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: something tried to validiate ID before database made");
            return false;
        }
        return tileDatabase.containsKey(key);
    }
    /**
     * Gets the classname only from the given tile ID
     * @param key The id to look at
     * @return The string representation of the class
     */
    public static String getClassName(String key) {
        if (tileDatabase == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: something tried to get class name before database made");
            return "None";
        }
        else if (!tileDatabase.containsKey(key)) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: given id " + key + " not found in database");
        }
        Class c = tileDatabase.get(key);
        // Appears as "class Class". E.g. "class SpeedBoost"
        // Shorten the string by converting it to a string, removing the "class" and any spaces then return.
        String value = String.valueOf(c).replaceAll("class", "").trim();
        return value;
    }
    /**
     * Sets the cell that this Tile is in as the given cell.
     * @param cell c The new cell this tile is in
     */
    public void setCell(Cell c) {
        this.cell = c;
    }
    /**
     * Replaces this Tile with the given tile.
     * @param t The tile to use now.
     */
    protected void replaceMe(Tile t) {
        cell.setTile(t);
    }
}
