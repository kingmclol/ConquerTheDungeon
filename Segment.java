import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * <p>Segments are an Actor that is simply meant to be a line.</p>
 * 
 * <p>This code is very old, and thus may be a bit difficult to use (written before ICS4U)</p>
 * 
 * @author Freeman Wang
 * @version December 2023 or something
 */
public class Segment extends Actor
{
    private Vector start, end;
    private GreenfootImage img = new GreenfootImage(50, 5);
    private Color color = Color.BLACK;
    private int thickness = 3;
    /**
     * Creates a segment from a start and end position of the given colour.
     * @param start the start pos
     * @param end the end pos
     * @param color color of line?
     */
    public Segment (Vector start, Vector end, Color color) {
        this(start, end);
        this.color = color;
    }
    /**
     * Creates a segment from a start to end position, with black color
     * @param start start pos
     * @param end end pos
     */
    public Segment (Vector start, Vector end) {
        this.start = start;
        this.end = end;
    }
    /**
     * Creates a segment from start to end, with thickness
     * @param start start pos
     * @param end end pos
     * @param thickness thickness of the segment
     */
    public Segment (Vector start, Vector end, int thickness) {
        this.start = start;
        this.end = end;
        this.thickness = thickness;
    }
    /**
     * Creates a segment of the given color only?
     * @param color The color of the segment
     */
    public Segment (Color color) {
        this.color = color;
    }
    /**
     * "Draws" the segment onto the world I think
     */
    public void draw() {
        if (start.displacementFrom(end).getMagnitude() <= 0.5) return;
        GreenfootImage img = new GreenfootImage(Utility.round(start.displacementFrom(end).getMagnitude()), thickness);
        img.setColor(color);
        //img.fill();
        setImage(img);
        setLocation(Utility.round(start.getX()), Utility.round(start.getY()));
        turnTowards(Utility.round(end.getX()), Utility.round(end.getY()));
        move(Utility.round(getImage().getWidth()/2));
    }
    /**
     * Update this segment to the new positions
     * @param start start pos
     * @param end end pos
     */
    public void update (Vector start, Vector end) {
        this.start = start;
        this.end = end;
        draw();
    }
    /**
     * Updates this segment relative to the start position?
     * @param start start pos
     * @param end end pos
     */
    public void updateRelative(Vector start, Vector end) {
        this.start = start;
        this.end = start.add(end);
        draw();
    }
    /**
     * Checks if it intersects a wall I guess since I didn't make this
     * @return whether the segment is intersecting a wall
     */
    public boolean intersectsWall() {
        Wall wall = (Wall)getOneIntersectingObject(Wall.class);
        if(wall == null){
            return false;
        }
        return true;
    }

}