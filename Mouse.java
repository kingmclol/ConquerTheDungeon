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
 * @version 2024-06-12
 */
public class Mouse  
{
    private static MouseInfo mouse;
    private static boolean mouseExists;
    private static World w;
    private static int x, y;
    private static boolean mouseDown;
    private static final boolean SHOW_INFO = false;
    private static Timer mouseDownTimer = new Timer(false);
    private Mouse()
    {
    }
    /**
     * Returns whether the mouse cliked on the given Actor.
     * @param a The actor to see if was clicked on.
     */
    public static boolean clickedOn(Actor actor) {
        if (!Greenfoot.mouseClicked(null) || !mouseExists) return false;
        List<Actor> objects = w.getObjectsAt(x,y, Actor.class);
        return objects.contains(actor);
    }
    /**
     * Returns an Actor of the same type as the class given. Will return null if the mouse did not
     * click, or does not exist. The given class must extend Actor.
     * @param c The class type to look for.
     * @return The topmost object found.
     */
    public static <A extends Actor> A getClickedActor(Class<A> c) {
        if (!Greenfoot.mouseClicked(null) || !mouseExists) return null; // If not clicked or mouse does not exist, then nothing to do.
        List<A> objects = w.getObjectsAt(x, y, c);
        if (objects.isEmpty()) return null;
        return objects.get(0); // Since getObjectsAt() returns an List<A>, the resulting element is also of type A
    }
    /**
     * Returns whether the mouse pointer is currently positioned above an instance of the given class.
     * @param c The class to look for
     * @return whether the mouse is hovering over an instance of c
     */
    public static boolean hoveringOver(Class c) {
        if (!Greenfoot.mouseMoved(null) || !mouseExists) return false;
        return w.getObjectsAt(x,y,c).isEmpty(); // Check if the mouse dragged positino is empty. empty = not dragged on it
    }
    /**
     * Gets mouse information. Returns true if successful, false if not. Must be run in world act.
     */
    public static void getMouse() {
        mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            x = mouse.getX();
            y = mouse.getY();
            if(SHOW_INFO) {
                System.out.printf("(%d, %d) | %b | hld: %d | ", x, y, mouseDown, mouseDownTimer.acts());
                for (Actor a : w.getObjectsAt(x, y, Actor.class)) {
                    System.out.print(a + " | ");
                }
                System.out.println();   
            }
            if (Greenfoot.mousePressed(null)) {
                mouseDown = true;
                mouseDownTimer.mark();
            } else if (Greenfoot.mouseClicked(null)) {
                mouseDown = false;
                mouseDownTimer.fullStop();
            }
            mouseExists = true;
        }
        else mouseExists = false;
    }
    /**
     * Returns whether the mouse is being held down or not at the current time.
     * @return whether the mouse is held is down or not.
     */
    public static boolean isMouseDown() {
        return mouseDown;
    }
    /**
     * Gets the position of the mouse as a Vector. returns null if mouse not found.
     * @return a Vector representing the position of the mouse. null if no mouse.
     */
    public static Vector getPosition() {
        if (!mouseExists) return null;
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
        if (!mouseExists) return null; // If not clicked or mouse does not exist, then nothing to do.
        List<A> objects = w.getObjectsAt(x, y, c);
        if (objects.isEmpty()) return null;
        return objects.get(0);
    }
}
