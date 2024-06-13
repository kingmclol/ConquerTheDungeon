import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>==================== Conquer the Dungeon ====================
 * <p>The IntroWorld is the first World that the player sees. It's only use is to look cool before the Player goes to the next World.
 * 
 * <p><strong>For the block comment, go to <code>README.md</code>.</strong>
 * 
 * @author Freeman Wang, Neelan
 * @version 2024-04-20
 */
public class IntroWorld extends SuperWorld
{
    private GreenfootSound introWorldMusic; 

    TextBox title;

    private int actCount;
    // Images from /u/Voidentir at https://old.reddit.com/r/DigitalArt/comments/1akfavq/my_old_landscape_artworks/
    private static String[] backgroundImages = new String[] {
        "landscape1.png",
        "landscape2.png",
        "landscape3.png"
    };
    //private TextBox buttonText2;
    private Picture currentImage;
    private Picture nextImage;
    private int index;
    private boolean loadingFromSave;
    private TextBox buttonText2;
    /**
     * Create an intro world where the music <strong>will not</strong> start automatically
     */
    public IntroWorld()
    {
        super();
        
        introWorldMusic = new GreenfootSound("intromusic.mp3");
        introWorldMusic.setVolume(50); 
        
        setPaintOrder( TextBox.class, Button.class, Picture.class);
        
        title = new TextBox("Conquer The Dungeon", 86, Color.BLACK, null, 2, 0);
        Button button = new Button(this::startGame, 200, 80);
        Button button2 = new Button(this::loadGame, 200, 80);
        Button deleteDataButton = new Button(this::deleteGame, 200, 80);
        TextBox buttonText1 = new TextBox("START", 32, Color.BLACK, null, 0, 255);
        TextBox buttonText2 = new TextBox("LOAD DATA", 32, Color.BLACK, null, 0, 255);        // This is a temporary prompt.)
        TextBox deleteDataText = new TextBox("DELETE DATA", 32, Color.BLACK, null, 0, 255);

        
        // introWorldMusic = new GreenfootSound("Introworld.mp3");
        // introWorldMusic.setVolume(30);
        
        actCount = 0;
        index = 0;
        loadingFromSave = false;
        
        currentImage = new Picture(backgroundImages[index]);
        currentImage.setTranslation(Utility.randomVector(0.2, 0.5, 0.075, 0.2));
        addObject(currentImage, getWidth()/2, getHeight()/2);
        
        nextImage = new Picture(backgroundImages[nextIndex()]);
        nextImage.setTranslation(Utility.randomVector(0.2, 0.5, 0.075, 0.2));
        nextImage.setTransparency(0); // hidden for now.
        addObject(title, getWidth()/2, 80);
        Greenfoot.setSpeed(50); // Control speed to 50.
        addObject(button, getWidth()/2, getHeight()/2);
        addObject(button2, getWidth()/2, getHeight()/2 + 100);
        addObject(buttonText1, getWidth()/2, getHeight()/2);
        addObject(buttonText2, getWidth()/2, getHeight()/2 + 100);
        addObject(deleteDataButton, getWidth()/2, getHeight()/2+200);
        addObject(deleteDataText, getWidth()/2, getHeight()/2+200);
        
        GameData.resetData();
    }
    /**
     * <p>Create an Introworld where music <strong>will</strong> start automatically.</p>
     * <p>The boolean passed has no effect. Do whatever you want.</p>
     * <p>Basically, the normal constructor without any parameters will not play the music first, so Greenfoot can
     * construct the World normally with the music starting only when the player presses the Run button. However,
     * when returning to the intro world from another world, the Run button is never pressed (since it's already running),
     * thus the started() would never run, leading to a music-less Intro world.</p>
     * <p>So, I simply made another constructor to overload it and then simply use that constructor instead, which would run the music
     * automatically<p>
     * @param overloadingConstructorSinceNoTimeForBetterSolution This is one of the parameters of all time.
     */
    public IntroWorld(boolean overloadingConstructorSinceNoTimeForBetterSolution) {
        this(); // Run the real constructor.
        introWorldMusic.playLoop(); // Run this one (1) line of code.
    }
    public void started() {
        introWorldMusic.playLoop();
    }
    public void stopped() {
        introWorldMusic.pause(); 
    }
    public void act() {
        super.act();

        if (++actCount >= 600) { // every 10 seconds, change background image.
            switchBackgroundImage();
            actCount = 0;
        }
        String key = Keyboard.getCurrentKey();
        if ("l".equals(key)) { // once L is pressed, move to the next world.
            introWorldMusic.stop(); 
            Greenfoot.setWorld(new StoryWorld(loadingFromSave));
        }
        else if ("i".equals(key)) { // Import data,
            if (GameData.importData()) {
                alert("Loaded from save...", new Color(40, 230, 70), getHeight()/2);
                loadingFromSave = true;
            } else { // not successful
                alert("Cannot load save data..", new Color(230, 70, 70), getHeight()/2);
            }
        }
        else if ("p".equals(key)) {
            
        }
    }
    /**
     * Switches the background image in a cool way.
     */
    private void switchBackgroundImage() {
        currentImage.fadeOut(1); // make the current image begin to disappear.
        addObject(nextImage,getWidth()/2, getHeight()/2); // add the next image.
        nextImage.fadeIn(1);
        // update reference of current image to the next image, as the current image will remove itself fro the world
        // by itself, so need to keep it anymore.
        currentImage = nextImage;
        nextImage = new Picture(backgroundImages[nextIndex()]); // have next image prepared.
        nextImage.setTransparency(0); // set its transparency to 0 so it can fade in.
        nextImage.setTranslation(Utility.randomVector(0.5, 1, 0.2, 0.5));
    }
    /**
     * Returns the next index for the next picture to use.
     */
    private int nextIndex() {
        index = (index + 1)%backgroundImages.length;
        return index;
    }
    /**
     * Starts the game
     */
    private void startGame(){
        introWorldMusic.stop();
        Greenfoot.setWorld(new StoryWorld(loadingFromSave));
    }
    /**
     * Attempts to load previous save.
     */
    private void loadGame(){
        if (GameData.importData()) {
            buttonText2.display("DATA LOADED!");
            alert("Loaded from save...", new Color(40, 230, 70), getHeight()-50);
            loadingFromSave = true;
        } else {
            alert("Cannot load save data..", new Color(230, 70, 70), getHeight()-50);
        }
    }
    /**
     * Deletes previous save.
     */
    private void deleteGame() {
        if (GameData.deleteData()) { // delete the save file.
            alert("Deleted previous save.", new Color(230, 70, 70), getHeight()-50);
            buttonText2.display("LOAD DATA"); // doens't really matter eh.
        }
        else { // Could not delete (most likely already deleted)
            alert("Already deleted.", new Color(230, 70, 70), getHeight()-50);
        }
        loadingFromSave = false; // reset this flag
    }
    // public void started() {
        // introWorldMusic.playLoop();
    // }
    // public void stopped() {
        // introWorldMusic.pause(); 
    // }
}