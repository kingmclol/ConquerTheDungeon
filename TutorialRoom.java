import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This is where you learn the controls for the game and how the game works.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TutorialRoom extends Room
{
    private String[] tutorialDialogue;
    private TextBox dialogueBox;
    private int line;
    /**
     * Constructor for objects of class TutorialRoom.
     * 
     */
    public TutorialRoom()
    {
        super(0, new Board(fallbackBuildString));
        line = 0;
        tutorialDialogue = new String[]
        {"WASD to move: ", "Left Click to attack", 
            "Attack Barrels to recieve powerups, \n heals and coins.", 
            "Press 'Q' to activate ultimate",
            "In Sword form, it enhances your slashes.(Ranged) \nIn Staff form, it shoots fireballs around you"};
        dialogueBox = new TextBox(tutorialDialogue[line], 24, Color.WHITE, null, 1, 100);
    }
    public void act()
    {
        super.act();
    }
}
