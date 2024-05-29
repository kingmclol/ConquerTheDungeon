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
    public Edge(int sx, int sy, int ex, int ey)
    {
        startX = sx;
        startY = sy;
        endX = ex;
        endY = ey;
    }

    public void extendEdgeTo(int ex, int ey) {
        endX = ex; 
        endY = ey;
    }
    public Vector getStart() {
        return new Vector(startX, startY);
    }
    public Vector getEnd() {
        return new Vector(endX, endY);
    }
}
