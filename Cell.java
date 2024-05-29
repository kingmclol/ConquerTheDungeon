import java.util.ArrayList;
import greenfoot.*;
/**
 * The Cell is the manager of everything that can happen within one unit of the Board. It manages the addition of CellEffects,
 * Enemies.
 * 
 * Cells can contain variuos CellEffects that are overlayed onto the Cell, along with Entities occupying the same Cell.
 * 
 * <p>They also hold essentially all the information that you would see in the World regarding the Board, such as
 * the Entities that reside within this Cell, the Tile associated with this Cell, and all of the CellEffects that exist.</p>
 * 
 * @author Freeman Wang
 * @version 0.1
 */
public class Cell extends SuperActor
{
    private Node node;
    private ArrayList<CellEffect> cellEffects;
    private Tile tile;
    private int boardX, boardY;
    private Board board; // easy reference to the board.
    /**
     * Creates a Cell with a Tile.
     * @param board The Board that the Cell belongs to.
     * @param boardX The x coordinate of this Cell on the Board.
     * @param boardY The y coordinate of this Cell on the Board.
     * @param t The Tile that this Cell should have.
     */
    public Cell(Board board, int boardX, int boardY, Tile t)
    {
        // Initialize variables.
        this.board = board; // prob can remove
        this.boardX = boardX;
        this.boardY = boardY;
        tile = t;
        cellEffects = new ArrayList<CellEffect>();
        node = new Node(true, boardX, boardY);
    }
    /**
     * When the Cell is added to the World, it will automagically add all of its corresponding Effects and Entities and its Tile.
     */
    public void addedToWorld(World w) {
        w.addObject(tile, getX(), getY()); // Tile at the background,
        for (CellEffect effect : cellEffects) { // Add the effects on the tile,
            w.addObject(effect, getX(), getY());
        }
    }
    public void act() {
        // if (Greenfoot.mouseClicked(this)) {
            // System.out.printf("(%d, %d)%n", boardX, boardY);
            // // Player player = GameWorld.getPlayer();
            // // System.out.println(Board.distanceFrom(this, player.getCell()));
            // // if (entities.contains(player)) {
                // // player.setIntent(Player.Intent.MOVEMENT); // useless for now.
                // // for (Cell c : board.getCellsInRadius(this, player.getMoveDistance())) {
                    // // if (!c.isWalkable()) continue;
                    // // c.applyEffect(new HighlightEffect(Color.CYAN));
                // // }
            // // } else if (player.getIntent() == Player.Intent.MOVEMENT && Board.distanceFrom(this, player.getCell()) <= player.getMoveDistance() && this.isWalkable()) {
                // // player.moveTo(this);
            // // }
        // }
    }
    /**
     * Given an CellEffect, apply that effect onto this Cell and add it to the World.
     * @param e The CellEffect to apply.
     */
    public void applyEffect(CellEffect e) {
        cellEffects.add(e);
        e.setCell(this);
        e.applyEffect();
        if (getWorld() != null) getWorld().addObject(e, getX(), getY()); // check if this cell is in the world first
    }
    /**
     * Given a CellEffect, remove that effect from this Cell and the World.
     * @param e The CellEffect to remove.
     */
    public void removeEffect(CellEffect e) {
        cellEffects.remove(e);
        e.removeEffect();
        if (e.getWorld() != null) getWorld().removeObject(e); // Check if the effect is in the world first
    }
    // public void addEntity(Entity e) {
        // // if (entities.contains(e)) return;
        // System.out.println("added " + e);
        // if (getWorld() != null) getWorld().addObject(e, getX(), getY());
    // }
    /**
     * Returns the Y coordinate of this Cell on the Board.
     */
    public int getBoardY() {
        return boardY;
    }
    /**
     * Returns the X coordinate of this Cell on the Board.
     */
    public int getBoardX() {
        return boardX;
    }
    /**
     * Returns the Node associated with this Cell.
     */
    public Node getNode() {
        node.setWalkable(isWalkable());
        return node;
    }
    /**
     * Set this Cell's node to be walkable or not.
     */
    public void setWalkable(boolean walkable) {
        node.setWalkable(walkable);
    }
    /**
     * Check this Cell's node if it is walkable.
     */
    public boolean isWalkable() {
        return node.isWalkable();
    }
    // /**
     // * Check through everything to see if this Cell contains a given object.
     // */
    // public boolean contains(Object o) {
        // if (cellEffects.contains(o) || entities.contains(o) || tile == o) return true;
        // return false;
    // }
    // public boolean containsEntity() {
        // return (!entities.isEmpty());
    // }
    public boolean containsCellEffect() {
        return (!cellEffects.isEmpty());
    }
}
