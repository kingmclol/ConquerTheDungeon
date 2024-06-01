/**
 * <p>Stores information about a collision<p>
 * 
 * <p>Code mostly taken from <a href="https://noonat.github.io/intersect/">this website</a>.</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-01
 */
public class Hit  
{
    // instance variables - replace the example below with your own
    private Vector pos; // The position of the collision?
    private Vector delta; // The required vector to displace to stop being in a collision state
    private Vector normal; // normal of the edge being collided?
    private float time; // unused, but meant for segment collisions
    /**
     * Constructor for objects of class Hit
     */
    public Hit()
    {
        pos = new Vector();
        delta = new Vector();
        normal = new Vector();
        time = 0;
    }
    /**
     * Set pos to the given vector.
     * @param v the vector to set pos to
     */
    public void setPos(Vector v) {
        pos = v;
    }
    /**
     * Set delta to the given vector.
     * @param v the vector to set delta to.
     */
    public void setDelta(Vector v) {
        delta = v;
    }
    /**
     * Set normal to the given vector.
     * @param v the vector to set normal to.
     */
    public void setNormal(Vector v) {
        normal = v;
    }
    /**
     * Get the position data of this hit
     * @return the position the collision occured
     */
    public Vector getPos() {
        return pos;
    }
    /**
     * Get the delta required for moving to a non-colliding state
     * @return the delta needed
     */
    public Vector getDelta() {
        return delta;
    }
    /**
     * Get the normal of the collision?
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }
}
