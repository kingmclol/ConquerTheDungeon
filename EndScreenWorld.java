import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * <p>The End screen World that shows the level that the player got to before dying.</p>
 * 
 * @author Osmond Lin
 * @version
 */
public class EndScreenWorld extends SuperWorld
{
    private GreenfootSound endScreenMusic = new GreenfootSound("endscreen.mp3");
    public EndScreenWorld()
    {    
        // Create a new world with 1200x768 cells, unbounded (infinite world)
        super(); 
        setPaintOrder(TextBox.class);
        // Set the background image or text
        GreenfootImage background = new GreenfootImage(1200, 768);
        background.setColor(Color.BLACK);
        background.fill();
        setBackground(background);
        TextBox title= new TextBox("GAME OVER", 86, Color.RED, null, 2, 0);
        addObject(title, getWidth()/2, 280);
        TextBox title2 = new TextBox("LEVEL: " + GameData.getLevel(), 86, Color.RED, null, 2, 0); 
        addObject(title2, getWidth()/2, 380);
        
        Room.setMusicState(false); // Stop the combat room music forcefully.
        
        BreathingTextBox prompt = new BreathingTextBox("Press any key to return.", 36, Color.WHITE, null, 180);
        addObject(prompt, getWidth()/2, getHeight()-100);

        endScreenMusic.playLoop();
        endScreenMusic.setVolume(50); 
    }
    public void started() {
        endScreenMusic.playLoop(); 
    }
    public void stopped() {
        endScreenMusic.pause(); 
    }
    public void act() {
        super.act();
        if (Keyboard.getCurrentKey() != null) { // user wants to return to title screen.
            endScreenMusic.stop();
            IntroWorld world = new IntroWorld();
            world.started(); // manually run this to start hte music
            Greenfoot.setWorld(world);
        }
    }
}



