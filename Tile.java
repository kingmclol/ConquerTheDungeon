import greenfoot.*;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Write a description of class Tile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Tile extends SuperActor
{
    public static boolean DRAW_BORDERS = true;
    /**
     * Constructor for objects of class Tile
     */
    protected boolean walkable;
    private static HashMap<String, Class> tileDatabase;
    private Cell cell;
    public Tile(boolean walkable, Color c)
    {
        GreenfootImage img = new GreenfootImage(Cell.SIZE, Cell.SIZE);
        this.walkable = walkable;
        img.setColor(c);
        img.fill();
        setImage(img);
    }
    public Tile (boolean walkable, GreenfootImage img) {
        img.scale(Cell.SIZE, Cell.SIZE);
        this.walkable = walkable;
        setImage(img);
    }
    public Tile (boolean walkable, Animation animation) {
        // ...
    }
    public void setImage(GreenfootImage img) {
        if (DRAW_BORDERS) {
            img.setColor(Color.BLACK);
            img.drawRect(0,0, Cell.SIZE, Cell.SIZE);
            //img.drawRect(0,0,GameWorld.tileSize, GameWorld.tileSize);
        }
        super.setImage(img);
    }
    public boolean isWalkable() {
        return walkable;
    }
    public abstract String getID();
    public static Tile getInstanceFromID(String id) {
        Class c = tileDatabase.get(id);
        if (c == null) {
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
        if (GameWorld.SHOW_LOGS) System.out.println("info: loaded in tile database");
    }
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
    public static boolean verifyID(String key) {
        if (tileDatabase == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: something tried to validiate ID before database made");
            return false;
        }
        return tileDatabase.containsKey(key);
    }
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
        // Shorten the string by converting v to a string, removing the "class" and any spaces then return.
        String value = String.valueOf(c).replaceAll("class", "").trim();
        return value;
    }
    /**
     * Sets the cell that this Tile is in as the given cell.
     */
    public void setCell(Cell c) {
        this.cell = c;
    }
    /**
     * Replaces this Tile with the given tile.
     */
    protected void replaceMe(Tile t) {
        cell.setTile(t);
    }
}
