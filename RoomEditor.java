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
    private String buildString = "16~12~w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/w/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/";
    Cell a, b;
    private String drawID;
    public RoomEditor()
    {    
        super();
        setPaintOrder(HealingEffect.class, Player.class);
        board = new Board(16, 12);
        addObject(board, 0,0);
        drawID = "f";
        SuperTextBox legend = new SuperTextBox(Tile.getLegend(), Color.GRAY, Color.WHITE, new Font("Calibri", 14), false, 176, 0, new Color(0,0,0,0));
        addObject(legend, 1024+(1200-1024)/2, getHeight()/2);
        addObject(new Player(), 30, 30);
    }
    public void act(){
        super.act();
        // Vector mousePosition = Mouse.getPosition();
        // if (mousePosition != null) {
            // board.rayCastToEdges((int)mousePosition.getX(), (int)mousePosition.getY());
        // }
        String key = Greenfoot.getKey();
       
        if (Greenfoot.mouseClicked(null)) {
            Cell c = Mouse.getClickedActor(Cell.class);
            if (c != null) {
                c.setTile(Tile.getInstanceFromID(drawID));
                board.convertTileMapToEdges();
                board.drawEdges();
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
        } else if ("/".equals(key)) {
            board.removeFromWorld();
            board = Room.getRandomBoard();
            addObject(board, 0,0);
        } else if ("e".equals(key)) {
            drawID = Greenfoot.ask("Give an ID of a tile to use.");
            TextBox info;
            if (Tile.verifyID(drawID)) {
                info = new TextBox("Now drawing with " + Tile.getClassName(drawID) + "s", 36, Color.WHITE, null, 2, 255);                
            } else {
                info = new TextBox("Given invalid ID " + drawID + ".", 36, Color.RED, null, 2, 255);
            }
            addObject(info, getWidth()/2, 100);
            info.fadeOut();
        }
    }
}
