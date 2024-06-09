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
    ArrayList<Edge> edgePool; //Not used anymore.
    ArrayList<Cell> validSpawnTiles;
    private int width, height;
    private static final String DELIM_DATA = "~";
    private static final String DELIM_CELL = "/";
    // Both are not used anymore, but here just in case I change my mind.
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
        validSpawnTiles = new ArrayList<Cell>();
        populate();
    }
    public Board(String buildString) {
        // Structure of 
        // "width~height~datad/d/d/d/d/d"
        edgePool = new ArrayList<Edge>();
        String[] elements = buildString.split(DELIM_DATA);
        width = Integer.valueOf(elements[0]);
        height = Integer.valueOf(elements[1]);
        worldMap = new Cell[height][width];
        validSpawnTiles = new ArrayList<Cell>();
        populate(elements[2]);
    }
    /**
     * Adds all of the other Cells to the World in this board.
     */
    public void addedToWorld(World w) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                w.addObject(worldMap[i][j], getX() + (j)*Cell.SIZE+Cell.SIZE/2, getY() + i*Cell.SIZE+Cell.SIZE/2);
            }
        }
        // All temp. not used anymore.
        // tempCanvas = new Picture(new GreenfootImage(Cell.SIZE*width, Cell.SIZE*height));
        // cast = new Picture(new GreenfootImage(Cell.SIZE*width, Cell.SIZE*height));
        // w.addObject(tempCanvas, getX() + width/2*Cell.SIZE, getY() + height/2*Cell.SIZE);
        // w.addObject(cast, getX() + width/2*Cell.SIZE, getY() + height/2*Cell.SIZE);
        // convertTileMapToEdges();
        // drawEdges();
    }
    /**
     * Removes this Board from the world, along with all of its cells.
     */
    public void removeFromWorld() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                worldMap[i][j].removeFromWorld();
            }
        }
        // getWorld().removeObject(cast);
        // getWorld().removeObject(tempCanvas);
        getWorld().removeObject(this); // Finally, remove the board itself.
    }
    /**
     * Generate the board from the given cell data in the structure of "d/f/df/e/g/d/" and so on.
     * @param cellData the data of the cells.
     */
    private void populate(String cellData) {
        // Structure of
        // "d/w/df/e/g/d"
        String[] cells = cellData.split(DELIM_CELL);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Parse through the data.
                Tile tile = Tile.getInstanceFromID(cells[y*width+x]);
                worldMap[y][x] = new Cell (this, x, y, tile);
                if (tile instanceof EmptyFloor) validSpawnTiles.add(worldMap[y][x]);
            }
        }
    }
    /**
     * Populates the map with EmptyFloors.
     */
    private void populate() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                worldMap[y][x] = new Cell(this, x, y, new EmptyFloor());
                validSpawnTiles.add(worldMap[y][x]);
            }
        }
    }
    /**
     * Get the representation of this Board as a String.
     * @return the buildString of this board.
     */
    public String getBuildString() {
        String buildString = width + DELIM_DATA + height + DELIM_DATA;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buildString+=worldMap[y][x].getTile().getID() + DELIM_CELL;
            }
        }
        return buildString;
    }
    /**
     * Prints out this Board's build string onto terminal.
     */
    public void outputBuildString() {
        System.out.println(getBuildString());
    }
    /**
     * Adds the given cell as a valid one for entitys to spawn in.
     * @param cell the cell to add. If already registered as spawnable, nothing happens.
     */
    public void addValidSpawnCell(Cell cell) {
        if (validSpawnTiles.contains(cell)) return; // already has
        else validSpawnTiles.add(cell);
    }
    /**
     * Marks the given cell as invalid for enemies to spawn in.
     * @param cell the cell to remove.
     */
    public void removeValidSpawnCell(Cell cell) {
        validSpawnTiles.remove(cell);
    }
    /**
     * Gets a random Cell marked as spawnable (an empty floor tile)
     * @return a random Cell that contains an EmptyFloor.
     */
    public Cell getRandomSpawnableCell() {
        return validSpawnTiles.get(Greenfoot.getRandomNumber(validSpawnTiles.size()));
    }
    /**
     * Returns a list of cells within the given radius around an origin cell using manhattan distance.
     * @param c The cell in the center (origin)
     * @param r The radius, in cells, calculated using manhattan distance (diagonal = 2 units)
     * @return The list of cells that are within the given radius around the cell.
     */
    public List<Cell> getCellsInRadius(Cell c, int r) {
        List<Cell> cells = new ArrayList<Cell>();
        int targetX = c.getBoardX();
        int targetY = c.getBoardY();
        int range = r;
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
    /**
     * Returns the cell at the given board coordinates. Returns null if nothing found.
     * @param boardX the X coordinate, in Cells.
     * @param boardY the Y coordinate, in Cells.
     * @return the Cell that resides at that coordinate pair or null if none found.
     */
    public Cell getCell(int boardX, int boardY) {
        if (boardX < 0 || boardX >= width || boardY < 0 || boardY >= height) return null;
        return worldMap[boardY][boardX];
    }
    /**
     * Given a Node, get the Cell that the node resides in.
     * @param n The node to look at
     * @param c the Cell that the node is in
     */
    public Cell getCell(Node n) {
        return getCell(n.getX(), n.getY());
    }
    /**
     * Given real (world) x and y coordinates, get the cell that exists at that position.
     * @param x the x coordinate in pixels.
     * @param y the y coordinate in pixels.
     * @return the Cell that resides at that position, null if none found.
     */
    public Cell getCellWithRealPosition(int x, int y) {
        int boardX = (int) (x - getX()) / Cell.SIZE;
        int boardY = (int) (y - getY()) / Cell.SIZE;
        return getCell(boardX, boardY);
    }
    /**
     * Gets the node that resides at the given coordinate in Cells.
     * @param boardX the x coordinate in Cells
     * @param boardY the y coordinate in cells
     * @return the Node that resides at that cooridnate, null it not found.
     */
    public Node getNode(int boardX, int boardY) {
        Cell c = getCell(boardX, boardY);
        if (c == null) return null;
        
        return c.getNode();
    }
    /**
     * Gets the Node given a Cell.
     * @param c the Cell to look at
     * @return the Node at the position where the cell is, null if not found
     */
    public Node getNode(Cell c) {
        if (c == null) return null;
        return getNode(c.getBoardX(), c.getBoardY());
    }
    /**
     * Applies a given CellEffect on to the given Cell.
     * @param e The cellEffect to apply.
     * @param c the Cell to apply the CellEffect on.
     */
    public void applyEffect(CellEffect e, Cell c) {
        c.applyEffect(e);
    }
    // public void applyEffect(CellEffect e, List<Cell> cells) {
        // for (Cell c : cells){
            // c.applyEffect(e.clone());
        // }
    // }
    /**
     * Spawns in the given Entity at the given Cell.
     * @param e The entity to spawn
     * @param c the cell the entity spawns in
     */
    public void addEntity(Entity e, Cell c) {
        // TODO: Add an warning effect/move this method to the cell?
        c.applyEffect(new EntitySpawn(e));
        //getWorld().addObject(e, c.getX(), c.getY());
    }
    /**
     * Returns the width of this board, in terms of Cells.
     * @return the width of the board.
     */
    public int width() {
        return width;
    }
    /**
     * Returns the height of this board, in terms of Cells.
     * @return the height of this board.
     */
    public int height() {
        return height;
    }
    /**
     * Calculates the distance between two cells using manhattan distance (diagnoals = 2 distance)
     * @param a The first cell.
     * @param b the second cell.
     * @return The distance between a and b using manhattan distance
     */
    public static int distanceFrom(Cell a, Cell b) {
        // Uses taxicab/manhattan gemoetry
        // The distance between P(p1, p2) and Q(q1, q2) is |p1-q1| + |p2-q2|
        return Math.abs(b.getBoardX()-a.getBoardX()) + Math.abs(b.getBoardY()-a.getBoardY());
    }
    /**
     * Returns the adjacent nodes to the given node. Diagonals included.
     * @param n The node to look at.
     * @return an List of neightbouring nodes.
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
    /**
     * Returns an List of Cells that paths from the start cell to end cell.
     * @param start The start cell
     * @param end the end cell
     * @param a list of cells representing the path.
     */
    public List<Cell> findPath(Cell start, Cell end) {
        return convertNodePathToCells(findPath(getNode(start), getNode(end)));
    }
    /**
     * A* pathfinding algorithm
     * @param start the starting position to path from, relative to grid.
     * @param end the ending position to be at, relative to grid
     * @return An array list of nodes representing the path
     */
    public ArrayList<Node> findPath(Node start, Node end) {
        if (start == null || end == null) return null;
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
        if (GameWorld.SHOW_LOGS) System.out.println("warn: no path was found in a pathfinding attempt");
        return null;
    } 
    /**
     * Given a list of nodes, convert them to a list of cells.
     * @param nodes The nodes to convert.
     * @return a list of cells from the given nodes.
     */
    private List<Cell> convertNodePathToCells(List<Node> nodes) {
        if (nodes == null) return null;
        List<Cell> cells = new ArrayList<Cell>();
        for (Node n : nodes) {
            cells.add(getCell(n));
        }
        return cells;
    }
    /**
     * Converts the given List of Nodes into a list of position Vectors.
     * @param nodes The list of nodes to convert.
     * @return a List of Vectors to travel to to follow the path.
     */
    public List<Vector> convertPathToPositions(List<Node> nodes) {
        if (nodes == null) return null;
        List<Vector> vectors = new ArrayList<Vector>();
        for (Node n : nodes) {
            vectors.add(new Vector(getX() + (n.getX() + 0.5)*Cell.SIZE, getY() + (n.getY() + 0.5)*Cell.SIZE));
        }
        return vectors;
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
    
    // ========poor attempt at shadowcasting, so please ignore. I gave up so its dead now.
    // So that's why the code is not the greatest + no comment, but I don't want to remove it.
    /**
     * Stolen code. Unused method. Do not use.
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
     * Only used for debuggin (scuffed). Do not use.
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
    /**
     * Do not use.
     */
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
