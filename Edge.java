/**
 * Write a description of class Edge here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Edge  
{
    private int startX, startY, endX, endY;
    /**
     * Constructor for objects of class Edge
     */
    public Edge(int sx, int sy)
    {
        startX = sx;
        startY = sy;
        endX = sx;
        endY = sy;
    }

    /**
     * Extends this edge either south (dir 0) or east (dir 1) by a given delta.
     * @param direction The direction to extend by. 0 for south, 1 for east.
     * @param delta How much to extend by.
     */
    public void extendSouth(int delta) {
        endY+= delta;
    }
    public void extendEast(int delta) {
        endX+= delta;
    }
    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }
    public int getEndX() {
        return endX;
    }
    public int getEndY() {
        return endY;
    }
    public String toString() {
        return String.format("(%d, %d) -> (%d, %d)", startX, startY, endX, endY);
    }
}
