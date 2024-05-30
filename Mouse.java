import greenfoot.*;
import java.util.List;
/**
 * This class is only meant to make it easier to work with getting information related to the mouse events.
 * 
 * To use this, you MUST set the world using Mouse.setWorld(World w) somewhere (preferably in the constructor
 * of your Worlds).
 * 
 * This class will regularly return null similarly to how the Greenfoot provided methods work (e.g. no mouse detected so mouse is null)
 * so be sure to have proper null pointer handling lest you get the dreaded NullPointerException.
 * 
 * @author Freeman Wang
 * @version (a version number or a date)
 */
public class Mouse  
{
    private static MouseInfo mouse;
    private static World w;
    private static int x, y;
    private Mouse()
    {
    }
    // public static <T extends Actor> T getClickedActor(T actor) {
        // getMouse();
        // if (mouse == null) return null;
        // else if (Greenfoot.mouseClicked(null)) {
            // return (T) mouse.getActor();
        // }
        // else return null;
    // }
    /**
     * 
     */
    public static boolean clickedOn(Actor a) {
        return Greenfoot.mouseClicked(a);
    }
    /**
     * Returns an Actor of the same type as the class given. Will return null if the mouse did not
     * click, or does not exist. The given class must extend Actor.
     * @param c The class type to look for.
     * @return The topmost object found.
     */
    public static <A extends Actor> A getClickedActor(Class<A> c) {
        if (!Greenfoot.mouseClicked(null) || !getMouse()) return null; // If not clicked or mouse does not exist, then nothing to do.
        List<A> objects = w.getObjectsAt(x, y, c);
        if (objects.isEmpty()) return null;
        return objects.get(0); // Since getObjectsAt() returns an List<A>, the resulting element is also of type A
    }
    /**
     * Returns 
     */
    public static boolean draggedOn(Class c) {
        if (!Greenfoot.mouseDragged(null) || !getMouse()) return false;
        return w.getObjectsAt(x,y,c).isEmpty(); // Check if the mouse dragged positino is empty. empty = not dragged on it
    }
    /**
     * Gets mouse information. Returns true if successful, false if not.
     */
    private static boolean getMouse() {
        mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            x = mouse.getX();
            y = mouse.getY();
            // System.out.printf("(%d, %d):", x, y);
            // for (Actor a : w.getObjectsAt(x, y, Actor.class)) {
                // System.out.print(a + " | ");
            // }
            // System.out.println();
            return true;
        }
        return false;
    }
    /**
     * Gets the position of the mouse as a Vector. returns null if mouse not found.
     * @return a Vector representing the position of the mouse. null if no mouse.
     */
    public static Vector getPosition() {
        if (!getMouse()) return null;
        return new Vector(x, y);
    }
    /**
     * Sets the World that Mouse should be analyzing positions & Actors in.
     * @param w The world that Mouse should be looking at.
     */
    public static void setWorld(World w) {
        Mouse.w = w;
    }
    /**
     * Returns an instance of the given class that is being hovered by the mouse, null if nothing found.
     * @param c The class to look for
     * @return an instance of that class hovered, or null if none found.
     */
    public static <A extends Actor> A getHoveredActor(Class<A> c) {
        if (!getMouse()) return null; // If not clicked or mouse does not exist, then nothing to do.
        List<A> objects = w.getObjectsAt(x, y, c);
        if (objects.isEmpty()) return null;
        return objects.get(0);
    }
}
