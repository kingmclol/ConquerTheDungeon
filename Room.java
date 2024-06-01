import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
/**
 * Write a description of class Room here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Room extends GameWorld
{

    /**
     * Constructor for objects of class Room.
     * 
     */
    private static ArrayList<String> roomTemplates;
    private Board board;
    public Room(Board b)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        board = b;
        
    }
    public Board getBoard() {
        return board;
    }
    public static void initializeRoomDatabase() {
        if (roomTemplates != null) return;
        roomTemplates = new ArrayList<String>();
        Scanner scan;
        try {
            scan = new Scanner(new File("buildStrings.txt"));
            while (scan.hasNextLine()) {
                roomTemplates.add(scan.nextLine());
            }
        } catch (Exception e){
            if (GameWorld.SHOW_LOGS) System.out.println("err: missing buildStrings.txt");
            // add a sad world...
            roomTemplates.add("16~12~f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/w/w/w/w/w/f/f/f/f/f/f/f/f/f/f/w/w/f/f/f/w/w/f/f/f/f/f/f/f/f/w/w/f/f/f/f/f/w/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/w/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/");
        }
        if (GameWorld.SHOW_LOGS) System.out.println("info: successfuly loaded in room database");
    }
    public static Board getRandomBoard() {
        String buildString = roomTemplates.get(Greenfoot.getRandomNumber(roomTemplates.size()));
        if (GameWorld.SHOW_LOGS) System.out.println(buildString);
        Board b = new Board(buildString);
        return b;
    }
}
