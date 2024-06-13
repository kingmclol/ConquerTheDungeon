import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * This is where you learn the controls for the game and how the game works.
 * 
 * @Tony Lin
 * @version (a version number or a date)
 */
public class TutorialRoom extends Room
{
    //Instance Variables: 
    private String[] tutorialDialogue;
    private static final String buildString = "16~12~f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/spb/f/f/f/f/f/f/f/f/f/f/f/f/spb/f/f/f/wd/wd/f/f/spb/slw/slw/spb/f/f/wd/wd/f/f/f/f/wd/b/f/f/f/f/f/f/f/f/b/wd/f/f/f/f/f/f/f/f/w/w/w/w/f/f/f/f/f/f/f/slw/f/f/f/f/b/ptl/ptl/b/f/f/f/f/slw/f/f/slw/f/f/f/f/b/ptl/ptl/b/f/f/f/f/slw/f/f/f/f/f/f/f/w/w/w/w/f/f/f/f/f/f/f/f/wd/b/f/f/f/f/f/f/f/f/b/wd/f/f/f/f/wd/wd/f/f/spb/slw/slw/spb/f/f/wd/wd/f/f/f/spb/f/f/f/f/f/f/f/f/f/f/f/f/spb/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/f/";
    private TextBox dialogueBox;
    private List<Barrel> b;
    private int line, acts = 0;
    private String currentDialogue, key;
    private boolean userPrompt = false, resetPlayerCds = false;
    private static int numOfBarrels;
    /**
     * Constructor for objects of class TutorialRoom.
     * 
     */
    public TutorialRoom()
    {
        super(0, new Board(buildString));
        line = 0;
        tutorialDialogue = new String[]
        {"WASD to move:", "Left Click to attack", 
            "Break Barrels for a chance to recieve powerups, \n heals and coins.", 
            "Press 'Q' to activate ultimate in Sword",
            "In Sword form, it enhances your slashes up to a certain number of times.\n(Ranged)In Staff form, it shoots fireballs around you",
            "Go to Portal to enter the dungeon!"};
        dialogueBox = new TextBox(tutorialDialogue[line], 24, Color.WHITE, null, 1, 100);
        addObject(dialogueBox, getWidth()/2, getHeight()/2-100);
        currentDialogue = tutorialDialogue[line];
        b = getObjects(Barrel.class);
        numOfBarrels = b.size();
    }

    public void act()
    {
        super.act();
        key = Greenfoot.getKey();
        if(checkNextDialogue())
        {
            nextLine();
        }
        acts++;
    }
    /**
     * Returns whether there is more dialogue for the tutorial.
     * 
     * @return     Returns True if there is more tutorial dialogue that hasn't been shown yet.
     */
    public boolean checkNextDialogue()
    {
        int nextLine = line + 1;
        return nextLine < tutorialDialogue.length;
    }
    /**
     * 
     */
    private void nextLine()
    {
        List<Player> p = getObjects(Player.class);
        switch(currentDialogue)
        {
            case "WASD to move:":
                for(Player player : p)
                {
                    if(player.getMoving())
                    {
                        userPrompt = true;
                        break;
                    }
                }
                break;
            case "Left Click to attack":
                for(Player player : p)
                {
                    if(player.state())
                    {
                        userPrompt = true;
                        break;
                    }
                }
                break;
            case "Break Barrels for a chance to recieve powerups, \n heals and coins.":
                b = getObjects(Barrel.class);
                if(b.size() < numOfBarrels)
                {
                    userPrompt = true;
                    addObject(dialogueBox, getWidth()/2, getHeight()/2-100);
                }
                break;
            case "Press 'Q' to activate ultimate in Sword":
                for(Player player : p)
                {
                    if(!resetPlayerCds)
                    {
                        player.resetCooldown();
                        resetPlayerCds = true;
                    }
                }
                if(("q").equals(Keyboard.getCurrentKey()))
                {
                    userPrompt = true;
                    addObject(dialogueBox, getWidth()/2, getHeight()/2-100);
                }
                break;
            case "In Sword form, it enhances your slashes up to a certain number of times.\n(Ranged)In Staff form, it shoots fireballs around you":
                for(Player player : p)
                {
                    if(!resetPlayerCds)
                    {
                        player.resetCooldown();
                        resetPlayerCds = true;
                    }
                    if(Greenfoot.isKeyDown("q") || (player.state() && player.isSwordUlt()))
                    {
                        userPrompt = true;
                        break;
                    }
                }
                break;
            case "Go to Portal to enter the dungeon!":
                return;
        }
        if(acts % 180 == 0 && userPrompt)
        {
            line++;
            removeObject(dialogueBox);
            currentDialogue = tutorialDialogue[line];
            dialogueBox =  new TextBox(currentDialogue, 24, Color.WHITE, null, 1, 100);
            addObject(dialogueBox, getWidth()/2, getHeight()/2-100); 
            userPrompt = false;
            resetPlayerCds = false;
        }
    }
}
