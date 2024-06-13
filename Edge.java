/**
 * <p>An unused class that was meant for trying to shadowcast/FOV</p>
 * 
 * @author Frreman Wang
 * @version 2024-06-12
 */
public class Edge  
{
    private int startX, startY, endX, endY;
    /**
     * Creates an edge with the starting position
     * @param sx start x
     * @param sy start y
     */
    public Edge(int sx, int sy)
    {
        startX = sx;
        startY = sy;
        endX = sx;
        endY = sy;
    }

    /**
     * Extends this edge south by a given amount
     * @param delta the amount to extend by
     */
    public void extendSouth(int delta) {
        endY+= delta;
    }
    /**
     * Extends this edge east by a given amount
     * @param delta the amount to extend by
     */
    public void extendEast(int delta) {
        endX+= delta;
    }
    /**
     * Returns start x
     */
    public int getStartX() {
        return startX;
    }
    /**
     * Returns start y
     */
    public int getStartY() {
        return startY;
    }
    /**
     * Returns end x
     */
    public int getEndX() {
        return endX;
    }
    /**
     * Returns end x
     */
    public int getEndY() {
        return endY;
    }
    public String toString() {
        return String.format("(%d, %d) -> (%d, %d)", startX, startY, endX, endY);
    }
}
