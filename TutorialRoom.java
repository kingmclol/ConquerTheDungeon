import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

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
    private int line, acts = 0;
    private String currentDialogue, key;
    private boolean userPrompt = false, resetPlayerCds = false;
    private static final int numOfBarrels = 7;
    /**
     * Constructor for objects of class TutorialRoom.
     * 
     */
    public TutorialRoom()
    {
        super(0, new Board(fallbackBuildString));
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

    public boolean checkNextDialogue()
    {
        int nextLine = line + 1;
        return nextLine < tutorialDialogue.length;
    }

    private void nextLine()
    {
        List<Player> p = getObjects(Player.class);
        List<Barrel> b = getObjects(Barrel.class);
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
