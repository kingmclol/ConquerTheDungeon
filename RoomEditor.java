import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
/**
 * Write a description of class RoomEditor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RoomEditor extends GameWorld
{

    /**
     * Constructor for objects of class RoomEditor.
     * 
     */
    //private String buildString = "16~12~w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/";
    //private String buildString = "16~12~w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/w/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/";
    private String buildString = "16~12~w/f/f/f/f/f/w/f/f/f/f/f/f/f/f/w/f/f/f/f/f/w/f/f/f/f/w/f/w/f/f/f/f/f/f/w/f/w/f/w/f/f/f/f/w/w/f/f/f/f/f/w/f/f/f/f/f/w/f/f/w/w/f/f/f/w/f/w/w/w/f/w/f/w/w/w/f/f/f/f/f/f/f/w/w/f/f/w/f/f/f/w/f/f/w/f/f/w/f/w/f/w/w/w/w/w/f/w/w/f/f/w/f/f/w/f/w/f/w/w/w/w/w/w/f/f/f/w/f/f/f/f/f/f/f/w/w/w/w/w/f/f/w/w/w/w/f/f/f/w/w/w/w/w/w/w/w/w/w/w/f/f/f/w/f/f/w/w/w/f/w/f/w/w/w/w/w/f/w/f/w/f/f/w/w/w/f/w/w/w/w/w/";
    private Cell a, b;
    private String drawID;
    public RoomEditor()
    {    
        super();
        board = new Board(16, 12);
        //board = Room.getRandomBoard();
        //board = new Board(buildString);
        addObject(board, 0,0);
        drawID = "f";
        SuperTextBox legend = new SuperTextBox(Tile.getLegend(), Color.GRAY, Color.WHITE, new Font("Calibri", 14), false, 176, 0, new Color(0,0,0,0));
        addObject(legend, 1024+(1200-1024)/2, getHeight()/2);
        //addObject(new Player(), 30, 30);
        //for (int i = 0; i < 5; i++) board.addEntity(new Goblin(), board.getRandomSpawnableCell());
    }
    public void act(){
        super.act();
        // Vector mousePosition = Mouse.getPosition();
        // if (mousePosition != null) {
            // board.rayCastToEdges((int)mousePosition.getX(), (int)mousePosition.getY());
        // }
        String key = Keyboard.getCurrentKey();
       
        if (Mouse.isMouseDown()) {
            Cell c = Mouse.getHoveredActor(Cell.class);
            if (c != null) {
                c.setTile(Tile.getInstanceFromID(drawID));
                // board.convertTileMapToEdges();
                // board.drawEdges();
            }
        }
        
        if (",".equals(key)) {
            a = Mouse.getHoveredActor(Cell.class);
            
        }
        else if (".".equals(key)) {
            b = Mouse.getHoveredActor(Cell.class);
            if (a == null || b == null) {
                System.out.println("missing node");
                return;
            }
            ArrayList<Cell> path = (ArrayList<Cell>) board.findPath(a,b);
            if (path == null) {
                System.out.println("no path");
                return;
            }
            for (int i = 0; i < path.size(); i++) {
                Cell c = path.get(i);
                c.applyEffect(new FadeEffect(Color.ORANGE, Utility.clamp(300/(path.size()-i), 0, 255)));
            }
        } else if ("p".equals(key)) {
            board.outputBuildString();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(board.getBuildString()), null);
            alert("Copied build string to clipboard.", Color.WHITE);
        } else if ("/".equals(key)) {
            board.removeFromWorld();
            board = Room.getRandomBoard();
            addObject(board, 0,0);
        } else if ("e".equals(key)) {
            drawID = Greenfoot.ask("Give an ID of a tile to use.");
            if (Tile.verifyID(drawID)) {
                alert("Now drawing with " + Tile.getClassName(drawID) + " tiles", Color.WHITE);
            } else {
                alert("Given invalid ID " + drawID + ".", Color.RED);
            }
        }
        else if ("\\".equals(key)) {
            for (int i = 0; i < 5; i++) board.addEntity(new Goblin(), board.getRandomSpawnableCell());
        }
    }
}
