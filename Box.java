import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;

/**
 * The Box is literally a box that exists for collision detection. It can be visibile or invisible
 * depending on what you want. It also can follow an "owner" actor to act as a hitbox, or something
 * like an indicator for an attack's range.
 * 
 * <p>But in the end, it's just a box. I swear.</p>
 * 
 * <blockquote>These box classes were written during the Vehicle Simulator, so please excuse the bad
 * code.</blockquote>
 * 
 * @author Freeman Wang
 * @version 2024-03-08
 */
public abstract class Box extends SuperActor
{
    private int height, width, xOffset, yOffset;
    private Actor owner;
    protected Color color;
    private boolean visible;
    private boolean initialAct; // Combat z sorting always running addedToWorld();
    public static final boolean SHOW_BOXES = false;
    // /**
     // * Creates a basic Box, which is basically a box. Should be used for temporary collision checking,
     // * so having it visible or invisible does not matter.
     // * @param width The width of the CollisionBox.
     // * @param height The height of the CollisionBox.
     // */
    // public Box (int width, int height) {
        // this(width, height, false, null, 0, 0);
    // }
    // /**
     // * Creates a CollisionBox that is tied to an owner.
     // * @param width The width of the CollisionBox.
     // * @param height The height of the CollisionBox.
     // * @param visible Whether the CollisionBox should be visible.
     // * @param owner The Actor that the CollisionBox should follow.
     // */
    // public Box(int width, int height, boolean visible, Actor owner){
        // this(width, height, visible, owner, 0, 0);
    // }
    /**
     * Creates a CollisionBox that is tied to an owner, but with an offset.
     * @param width The width of the CollisionBox.
     * @param height The height of the CollisionBox.
     * @param visible Whether the CollisionBox should be visible.
     * @param owner The Actor that the CollisionBox should follow.
     * @param xOffset The x offset in pixels, for the CollisionBox's following.
     * @param yOffset The y offset in pixels, for the CollisionBox's following.
     */
    public Box(int width, int height, boolean visible, Actor owner, int xOffset, int yOffset) {
        setImage(new GreenfootImage(width, height));
        this.width = width;
        this.height = height;
        this.visible = visible;
        this.owner = owner;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        initialAct = true;
    }
    public void addedToWorld(World w){
        if (!initialAct) return;
        // Make the Box visible, if needed.
        if (visible) {
            getImage().setColor(color);
            getImage().fill();
        }
        
        initialAct = false;
    }
    public void act() {
        // Remove any orphaned Boxes. They should not exist to act without an owner. If used for instant
        // collision checks, they should've been removed anyways.
        if (owner == null || owner.getWorld() == null) { 
            getWorld().removeObject(this);
            return;
        }
        setLocation(owner.getX()+xOffset, owner.getY()+yOffset); // Match with the owner, factoring in the yOffset.
    }
    /**
     * Returns whether this Box is intersecting an instance of the 
     * given Class.
     * @param c The Class to look for.
     * @return Whether the Box is touching an object of the Class or not.
     */
    public boolean isIntersecting(Class c) {
        return isTouching(c); // Check for intersetion normally.
    }
    /**
     * Makes the Box visible.
     */
    public void makeVisible() {
        getImage().setColor(color);
        getImage().fill();
        visible = true;
    }
    /**
     * Makes the Box (probably) invisible.
     */
    public void makeInvisible() {
        getImage().clear();
        visible = false;
    }
    /**
     * Returns an intersecting object of the given Class.
     * @param c The Class to look for.
     * @return The Actor being intersected. Returns null if none found.
     */
    public Actor getOneIntersectingObject(Class c) {
        return super.getOneIntersectingObject(c); // Haha, no recursion this time!
    }
    /**
     * Returns the owner of the Box. Returns null if the Box does not have one,
     * even though at that point the Box shouldn't be existing in the first place.
     * @return the owner of this box, null if none
     */
    public Actor getOwner() {
        return owner;
    }
    /**
     * Returns whether the Box is visible.
     */
    public boolean getVisibility() {
        return visible;
    }
    /**
     * Gets the width of this box
     * @return width
     */
    public int getWidth() {
        return width;
    }
    /**
     * Gets the height of this box
     * @return height
     */
    public int getHeight() {
        return height;
    }
    /**
     * Sets a color to this box, if you want a more unique one. You won't see anything if its not visible by the way
     * @param c The color to use
     */
    public void setColor(Color c) {
        GreenfootImage img = getImage();
        color = c;
        img.setColor(c);
        if (visible) {
            img.fill();
        }
    }
    public void forcePositionUpdate() {
        if (owner == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: box " + this + " attempted to update position when owner is null");
            return;
        }
        setLocation(owner.getX()+xOffset, owner.getY()+yOffset); // Match with the owner, factoring in the yOffset.
    }
    public List<Actor> getIntersectingActors(Class<Actor> cls) {
        return super.getIntersectingObjects(cls);
    }
    /**
     * Retuns all intersecting boxes of the given box subclass.
     * @param c the class of objects to look for. Should extend box
     * @return a list of the boxes being intersected.
     */
    public <A extends Box> List<A> getIntersectingBoxes(Class<A> c) {
        return getIntersectingObjects(c);
    }
    // The following methods are from noonat.github.io/intersect
    /**
     * Given a box, determine if a collision occured and any hit data. Borrowed code.
     * @param box The box that is being checked for collision. This is the box that should be pushed away.
     * @return a hit object if a collision occured, null it none did.
     */
    public Hit intersectAABB(Box box) {
        // Determine the deltaX. This is the distance between the two boxes.
        int dx = box.getX() - this.getX();
        // Determine the sum of both box half-widths. deltaX must be greater than the sum for there to be no collision.
        // So, subtracting abs(deltaX), if the result is negative, means that deltaX > sum(a_half-width, b_half-width)
        // and no collision occured (return null)
        int px = (box.getWidth()/2 + this.getWidth()/2) - Math.abs(dx);
        if (px <= 0 ) return null;
        
        // Similar process to above to the Y values.
        int dy = box.getY() - this.getY();
        int py = (box.getHeight()/2 + this.getHeight()/2) - Math.abs(dy);
        if (py <= 0 ) return null;
        
        // Getting here means that a collision did indeed occur.
        Hit hit = new Hit ();
        if (px < py) { // Determine whether the collision is deeper in the x or y direction?
            // should be pushed in the x direction.
            double sx = Math.signum(dx); // Get direction of push
            hit.setDelta(new Vector(px * sx, 0)); // create delta vector to push into non-colliding state (overlap * direction)
            hit.setNormal(new Vector(sx, 0));
            hit.setPos(new Vector(this.getX() + (this.getWidth()/2 * sx), box.getY()));
        } else {
            // pushed in the y direction. similar logic as before.
            double sy = Math.signum(dy);
            hit.setDelta(new Vector(0, py * sy));
            hit.setNormal(new Vector(0, sy));
            hit.setPos(new Vector(box.getX(), this.getY() + (this.getHeight()/2 * sy)));
        }
        return hit;
    }
    
    // public Hit intersectSegement(Vector pos, Vector delta, int paddingX, int paddingY) {
        
    // }
}
