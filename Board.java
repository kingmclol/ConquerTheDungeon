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
    ArrayList<Edge> edgePool;
    private int width, height;
    private static final String DELIM_DATA = "~";
    private static final String DELIM_CELL = "/";
    private static Picture tempCanvas;
    private static Picture cast;
    /**
     * Constructor for objects of class Board
     */
    public Board(int lengthX, int lengthY)
    {
        height = lengthY;
        width = lengthX;
        edgePool = new ArrayList<Edge>();
        worldMap = new Cell[height][width];
        populate(worldMap);
    }
    public Board(String buildString) {
        // Structure of 
        // "width~height~datad/d/d/d/d/d"
        edgePool = new ArrayList<Edge>();
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
        // All temp.
        tempCanvas = new Picture(new GreenfootImage(Cell.SIZE*width, Cell.SIZE*height));
        cast = new Picture(new GreenfootImage(Cell.SIZE*width, Cell.SIZE*height));
        w.addObject(tempCanvas, getX() + width/2*Cell.SIZE, getY() + height/2*Cell.SIZE);
        w.addObject(cast, getX() + width/2*Cell.SIZE, getY() + height/2*Cell.SIZE);
        convertTileMapToEdges();
        drawEdges();
    }
    public void removeFromWorld() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                worldMap[i][j].removeFromWorld();
            }
        }
        getWorld().removeObject(cast);
        getWorld().removeObject(tempCanvas);
        getWorld().removeObject(this); // Finally, remove the board itself.
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
    public String getBuildString() {
        String buildString = width + DELIM_DATA + height + DELIM_DATA;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buildString+=worldMap[y][x].getTile().getID() + DELIM_CELL;
            }
        }
        return buildString;
    }
    public void outputBuildString() {
        System.out.println(getBuildString());
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
        if (boardX < 0 || boardX >= width || boardY < 0 || boardY >= height) return null;
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
        if (SuperWorld.SHOW_LOGS) System.out.println("warn: no path was found in a pathfinding attempt");
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
    
    // ========poor attempt at shadowcasting, so please ignore,
    /**
     * Stolen code
     */
    public void convertTileMapToEdges() {
        edgePool.clear();
        // Clear existing edge data.

        // Easier use of finding proper edges.
        int N = 0;
        int S = 1;
        int E = 2;
        int W = 3;
        for (int tileY = 0; tileY < height; tileY++) {
            for (int tileX = 0; tileX < width; tileX++) {
                worldMap[tileY][tileX].clearEdgeData();
            }
        }
        // Iterate throughout all cells, collecting edges
        for (int tileY = 0; tileY < height; tileY++) {
            for (int tileX = 0; tileX < width; tileX++) {
                // Get neighbouring cells.
                // Edges defined as
                // N S E W
                // 0 1 2 3
                Cell current = getCell(tileX, tileY);
                Cell north = getCell(tileX, tileY-1);
                Cell south = getCell(tileX, tileY+1);
                Cell east = getCell(tileX+1, tileY);
                Cell west = getCell(tileX-1, tileY);

                // Check if the current cell is an obstruction. If not, it has no edges (skip)
                if (!current.isWalkable()) {
                    // Check for western unobstructed tile. If dne, then this cell needs a western edge.
                    if (west!=null && west.isWalkable()) {
                        // Check if the north cell is obstructed and has a western edge. If so, extend that edge to this cell.
                        if (north != null && north.edgeExist(W)) {
                            edgePool.get(north.getEdgeID(W)).extendSouth(Cell.SIZE);
                            current.setEdgeID(W, north.getEdgeID(W));
                            current.setEdgeExist(W, true);
                        }
                        else { // Create a new edge.
                            // Take an edge of the top left corner of he cell in question.
                            // And extend it south.
                            Edge edge = new Edge(getX() + tileX * Cell.SIZE, getY() + tileY * Cell.SIZE);
                            edge.extendSouth(Cell.SIZE);

                            // Add the new edge to the edge pool.
                            int edgeID = edgePool.size();
                            edgePool.add(edge);

                            // Update cell information
                            current.setEdgeExist(W, true);
                            current.setEdgeID(W, edgeID);
                        }
                    }
                    // Check for eastern unobstructed tile. If dne, then this cell needs a eastern edge.
                    if (east!=null && east.isWalkable()) {
                        // Check if the north cell is obstructed and has a western edge. If so, extend that edge to this cell.
                        if (north != null && north.edgeExist(E)) {
                            edgePool.get(north.getEdgeID(E)).extendSouth(Cell.SIZE);
                            current.setEdgeID(E, north.getEdgeID(E));
                            current.setEdgeExist(E, true);
                        }
                        else { // Create a new edge.
                            // Take an edge of the top RIGHT corner of the cell in question.
                            // And extend it south.
                            Edge edge = new Edge(getX() + (tileX+1) * Cell.SIZE, getY() + tileY * Cell.SIZE);
                            edge.extendSouth(Cell.SIZE);

                            // Add the new edge to the edge pool.
                            int edgeID = edgePool.size();
                            edgePool.add(edge);

                            // Update cell information
                            current.setEdgeExist(E, true);
                            current.setEdgeID(E, edgeID);
                        }
                    }
                    // Check for northern unobstructed tile. If dne, then this cell needs a northern edge.
                    if (north!=null && north.isWalkable()) {
                        // Check if the wets cell is obstructed and has a northern edge. If so, extend that edge to this cell.
                        if (west != null && west.edgeExist(N)) {
                            edgePool.get(west.getEdgeID(N)).extendEast(Cell.SIZE);
                            current.setEdgeID(N, west.getEdgeID(N));
                            current.setEdgeExist(N, true);
                        }
                        else { // Create a new edge.
                            // Take an edge of the top left corner of he cell in question.
                            // And extend it east.
                            Edge edge = new Edge(getX() + tileX * Cell.SIZE, getY() + tileY * Cell.SIZE);
                            edge.extendEast(Cell.SIZE);

                            // Add the new edge to the edge pool.
                            int edgeID = edgePool.size();
                            edgePool.add(edge);

                            // Update cell information
                            current.setEdgeExist(N, true);
                            current.setEdgeID(N, edgeID);
                        }
                    }
                    // Check for South unobstructed tile. If dne, then this cell needs a south edge.
                    if (south!=null && south.isWalkable()) {
                        // Check if the west cell is obstructed and has a south edge. If so, extend that edge to this cell.
                        if (west != null && west.edgeExist(S)) {
                            edgePool.get(west.getEdgeID(S)).extendEast(Cell.SIZE);
                            current.setEdgeID(S, west.getEdgeID(S));
                            current.setEdgeExist(S, true);
                        }
                        else { // Create a new edge.
                            // Take an edge of the bottom left corner of he cell in question.
                            // And extend it east.
                            Edge edge = new Edge(getX() + tileX * Cell.SIZE, getY() + (tileY+1) * Cell.SIZE);
                            edge.extendEast(Cell.SIZE);

                            // Add the new edge to the edge pool.
                            int edgeID = edgePool.size();
                            edgePool.add(edge);

                            // Update cell information
                            current.setEdgeExist(S, true);
                            current.setEdgeID(S, edgeID);
                        }
                    }
                }
            }
        }
    }
    /**
     * Only used for debuggin (scuffed)
     */
    public void drawEdges() {
        GreenfootImage img = tempCanvas.getImage();
        img.clear();
        img.setColor(Color.WHITE);
        for (Edge e : edgePool) {
            img.drawLine(e.getStartX(), e.getStartY(), e.getEndX(), e.getEndY());
            img.fillOval(e.getStartX()-3, e.getStartY()-3, 6, 6);
            img.fillOval(e.getEndX()-3, e.getEndY()-3, 6,6);
        }
    }
    public void rayCastToEdges(int originX, int originY){
        GreenfootImage img = cast.getImage();
        img.clear();
        img.setColor(Color.RED);
        for (Edge e: edgePool) {
            img.drawLine(originX, originY, e.getStartX(), e.getStartY());
            img.drawLine(originX, originY, e.getEndX(), e.getEndY());
        }
    }
}
