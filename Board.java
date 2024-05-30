import greenfoot.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
/**
 * The Board holds everything about the actual game positions. It has a 2D array of Cells, and manages pathfinding within the board.
 * 
 * To use the Board, add it to the world somwehre. It will automatically add all of the other cells, with wherever the board was added
 * acting as the TOP LEFT corner of the overall map.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Board extends Actor
{
    Cell[][] worldMap;
    // ArrayList<Edge> edgePool;
    private int width, height;
    private static boolean SHOW_LOGS = true;
    private static final String DELIM_DATA = "~";
    private static final String DELIM_CELL = "/";
    /**
     * Constructor for objects of class Board
     */
    public Board(int lengthX, int lengthY)
    {
        height = lengthY;
        width = lengthX;
        // edgePool = new ArrayList<Edge>();
        worldMap = new Cell[height][width];
        populate(worldMap);
    }
    public Board(String buildString) {
        // Structure of 
        // "width~height~datad/d/d/d/d/d"
        String[] elements = buildString.split(DELIM_DATA);
        width = Integer.valueOf(elements[0]);
        height = Integer.valueOf(elements[1]);
        worldMap = new Cell[height][width];
        populate(worldMap, elements[2]);
    }
    public void addedToWorld(World w) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                w.addObject(worldMap[i][j], getX() + (j)*Cell.SIZE+Cell.SIZE/2, getY() + i*Cell.SIZE+Cell.SIZE/2);
            }
        }
    }
    private void populate(Cell[][] map, String cellData) {
        // Structure of
        // "d/w/df/e/g/d"
        String[] cells = cellData.split(DELIM_CELL);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Parse through the data.
                Tile tile = Tile.getInstanceFromID(cells[y*width+x]);
                worldMap[y][x] = new Cell (this, x, y, tile);
            }
        }
    }
    private void populate(Cell[][] map) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                worldMap[y][x] = new Cell(this, x, y, new EmptyFloor());
            }
        }
    }
    public void outputBuildString() {
        String buildString = width + DELIM_DATA + height + DELIM_DATA;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buildString+=worldMap[y][x].getTile().getID() + DELIM_CELL;
            }
        }
        System.out.println(buildString);
    }
    public List<Cell> getCellsInRadius(Cell c, double r) {
        List<Cell> cells = new ArrayList<Cell>();
        int targetX = c.getBoardX();
        int targetY = c.getBoardY();
        int range = (int) Math.ceil(r); // Round r up.
        for (int y = targetY-range; y <= targetY + range; y++) {
            for (int x = targetX-range; x <= targetX + range; x++) {
                if (y < 0 || y > height-1 || x < 0 || x > width-1) continue; // If out of bounds just skip...
                if (distanceFrom(c, getCell(x, y)) <= r) { // If this cell is within the given radius in cells,
                    cells.add(getCell(x,y)); // add it to the list.
                }
            }
        }
        return cells;
    }
    public Cell getCell(int boardX, int boardY) {
        return worldMap[boardY][boardX];
    }
    public Cell getCell(Node n) {
        return getCell(n.getX(), n.getY());
    }
    public Node getNode(int boardX, int boardY) {
        return getCell(boardX, boardY).getNode();
    }
    public Node getNode(Cell c) {
        return getNode(c.getBoardX(), c.getBoardY());
    }
    public void applyEffect(CellEffect e, Cell c) {
        c.applyEffect(e);
    }
    // public void applyEffect(CellEffect e, List<Cell> cells) {
        // for (Cell c : cells){
            // c.applyEffect(e.clone());
        // }
    // }
    public void addEntity(Entity e, int boardX, int boardY) {
        // worldMap[boardY][boardX].addEntity(e);
        // Calculate proper world coordinates to add?
    }
    public int width() {
        return width;
    }
    public int height() {
        return height;
    }
    public static double distanceFrom(Cell a, Cell b) {
        // Uses taxicab/manhattan gemoetry
        // The distance between P(p1, p2) and Q(q1, q2) is |p1-q1| + |p2-q2|
        return Math.abs(b.getBoardX()-a.getBoardX()) + Math.abs(b.getBoardY()-a.getBoardY());
    }
    /**
     * Returns the adjacent nodes to the given node. Diagonals included.
     */
    public ArrayList<Node> getNeighbours(Node n) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        int nodeX = n.getX();
        int nodeY = n.getY();
        for (int y = -1; y <=1; y++) { // Iterate through possible y values (top row, current row, bottom row)
            for (int x = -1; x <= 1; x++) { // Iterate through possible x vslues (left row, current row, right row)
                if (x == 0 && y == 0) continue; // If center (current tile), skip.
                
                int checkX = nodeX + x;
                int checkY = nodeY + y;
                
                // If the coordinate is within the bounds of the grid,
                if (checkX >= 0 && checkX < width && checkY >= 0 && checkY < height) {
                    Node neighbour = getNode(checkX, checkY);
                                    
                    neighbours.add(neighbour); // add it to be returned
                }
            }
        }
        return neighbours;
    }
    public List<Cell> findPath(Cell start, Cell end) {
        return convertNodePathToCells(findPath(getNode(start), getNode(end)));
    }
    /**
     * A* pathfinding algorithm
     * @param start the starting position to path from, relative to grid.
     * @param end the ending position to be at, relative to grid
     */
    public ArrayList<Node> findPath(Node start, Node end) {
        // Create nodes based on the given positions.
        Node startNode = start;
        Node endNode = end;
        
        // Create two lists.
        ArrayList<Node> openSet = new ArrayList<Node>(); // Holds unexplored, accessbile nodes?
        ArrayList<Node> closedSet = new ArrayList<Node>(); // Holds explored nodes?
        
        openSet.add(startNode); // Begin with exploring aroud the start node.
        
        while (openSet.size() > 0) { // While there are still nodes to explore,
            Node currentNode = openSet.get(0); // Get the 0th node.
            for (int i = 1; i < openSet.size(); i++) { // Iterate through the other open nodes.
                Node n = openSet.get(i);
                // If n is more efficent to get to the ending node, then use that node instead.
                if (n.fCost() < currentNode.fCost() || n.fCost() == currentNode.fCost() && n.hCost() < currentNode.hCost()) {
                    currentNode = openSet.get(i);
                }
            }
            
            // Remove the current node from the open set (currently is exploring from it)
            openSet.remove(currentNode);
            closedSet.add(currentNode); // Add the node into the closed set (since after were done we have finished exploring
            
            // if (currentNode == endNode) { // uses references instead, MUCH BETTER
            if (currentNode == endNode) { // If the current node is equal to the end node (path found);
                return retracePath(startNode, currentNode); // Return the path found.
            }
            
            for (Node neighbour : getNeighbours(currentNode)) { // For each adjacent tile to the current node,
                if (!neighbour.isWalkable() || closedSet.contains(neighbour)) { // If this neighbouring tile is not walkable, or has been cheked already,
                    continue; // skip.
                }
                // Calculate the cost to get to the neighbouring node.
                int moveCostToNeighbour = currentNode.gCost() + Node.getDistance(currentNode, neighbour);
                
                // If the move cost to the neighbour is more efficient, and it is not already existing.
                if (moveCostToNeighbour < neighbour.gCost() || !openSet.contains(neighbour)) {
                    neighbour.setGCost(moveCostToNeighbour);
                    neighbour.setHCost(Node.getDistance(neighbour, endNode));
                    neighbour.setParent(currentNode);
                    
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }
        if (SHOW_LOGS) System.out.println("warn: no path was found in a pathfinding attempt");
        return null;
    } 
    private List<Cell> convertNodePathToCells(List<Node> nodes) {
        if (nodes == null) return null;
        List<Cell> cells = new ArrayList<Cell>();
        for (Node n : nodes) {
            cells.add(getCell(n));
        }
        return cells;
    }
    /**
     * Retraces the path taken, given a starting node and ending node.
     * @param startNode the starting node
     * @param endNode the ending node.
     * @return The actual path from start node to end node, in order.
     */
    private ArrayList<Node> retracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currentNode = endNode; // Start with the ending node.
        path.add(currentNode); // Add it to the path.
        while (currentNode != startNode) { // While the current node is not the starting node (still need to backtrack). Same issue, currentNode != startNode is much better
            currentNode = currentNode.getParent(); // get the parent of the current node, and
            path.add(currentNode); // add it to the path.
        }
        Collections.reverse(path); // Reverse the path, so it goes from start -> end instead of end -> start.
        return path;
    }
}
