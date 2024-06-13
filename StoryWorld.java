import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Story world introduces the simulation to the player. It is self-serving, and there should not be any need
 * to do something here somewhere else not in this world.
 * 
 * @author Freeman Wang
 * @version 2024-04-20
 */
public class StoryWorld extends SuperWorld
{
    private GreenfootSound storyWorldMusic;
    private String[] dialogue;
    private TextBox dialogueBox;
    private BreathingTextBox promptBox;
    private Button nextWorldButton;
    private int line;
    private int visibleActCount;
    private Button skipButton;
    /**
     * Constructor for objects of class StoryWorld.
     * @param loadingFromSave whether this is from a new save.
     */
    public StoryWorld(boolean loadingFromSave)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(); 
        visibleActCount = 0;
        GreenfootImage backgroundImage = new GreenfootImage(1024, 768);
        backgroundImage.setColor(Color.BLACK);
        backgroundImage.fill();
        setBackground(backgroundImage);
        
        storyWorldMusic = new GreenfootSound("storyworld.mp3");
        storyWorldMusic.playLoop();
        
        if (!loadingFromSave) { // New game!
            dialogue = new String[]{
                "Hello.",
                "I am very sorry. Very sorry indeed.",
                "You are dead. Hit by a runaway truck.",
                "Thankfully, I am a forgiving god.",
                "You are given another chance. Reincarnation.",
                "Let's see. . .",
                "Ah. This world seems fine.",
                "A dungeon crawler. Endless combat.",
                "I think you'll do just fine.",
                "Don't ask why. Gods are an enigma, forever unsolved.",
                "Good luck, and goodbye."
            };
        } else { // Loading from save.
            dialogue = new String[]{
                "Welcome back.",
                "Did you have a good break?",
                "The fighting never ends.",
                "Don't tarry too long, now."
            };
        }
        line = 0;
        
        dialogueBox = new TextBox(dialogue[line], 24, Color.WHITE, null, 1, 100);
        addObject(dialogueBox, getWidth()/2, getHeight()/2-100);
        
        skipButton = new Button(this::goToNextWorld, 120, 21, new GreenfootImage("skipbutton_idle_v2.png"), new GreenfootImage("skipbutton_hovered_v2.png"), new GreenfootImage("skipbutton_pressed_v2.png"));
        addObject(skipButton, getWidth()-60 - 10, 10 + 10);
        
        promptBox = new BreathingTextBox("Click to continue...", 18, Color.WHITE, null, 240);
        //nextWorldButton = new Button(() -> goToNextWorld(), 200, 75, new GreenfootImage("goButton.png"), new GreenfootImage("goButtonHovered.png"), new GreenfootImage("goButtonPressed.png"));
    }
    public void started() {
        storyWorldMusic.playLoop();
    }
    public void stopped() {
        storyWorldMusic.pause();
    }
    public void act() {
        super.act();
        if (!stillMoreDialogue() && dialogueBox.getWorld() == null) {
            goToNextWorld();
        }
        if (Greenfoot.mousePressed(null)) {
            if (stillMoreDialogue()) { // progresses dialogue, if still exists.
                visibleActCount = 0;
                playDialogue(++line);
                if (promptBox.getWorld() != null) removeObject(promptBox);
            } else { // once dialogue is exhausted, fade the textbox and tansition to next world
                dialogueBox.fadeOut();
                //addObject(nextWorldButton, getWidth()/2, getHeight()/2 + 50);
            }
        }
        
        if (dialogueBox.isVisible()) { // count acts since the dialogue box is max transparency
            visibleActCount++;
        }
        if (visibleActCount >= 180 && stillMoreDialogue() && promptBox.getWorld() == null) { // determine if need to add a prompt to click to continue
            addObject(promptBox, getWidth()/2, getWidth()/2 + 100);
        }
    }
    /**
     * Goes to the next world.
     */
    private void goToNextWorld() {
        storyWorldMusic.stop();
        GameWorld.goToLevel(GameData.getLevel());
    }
    /**
     * Progresses the main dialogue.
     */
    private void playDialogue(int line) {
        dialogueBox.display(dialogue[line]);
    }
    /**
     * Check whether all dialogue has been exhausted.
     * @return true if there still is more dialogue.
     */
    private boolean stillMoreDialogue() {
        int nextLine = line + 1;
        return nextLine < dialogue.length;
    }
}
