import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>A Pop Up Box functions as a tooltip that displays a textbox when the cursor hovers over a specific area for a short time.</p>
 * <p>It is useful for displaying relevant info and text while not cluttering up the screen.
 * @author Neelan Thurairajah
 * @version June 2024
 */
public class PopUpBox extends UI
{

    private SuperTextBox text;
    private boolean fadeIn;
    private boolean fadeOut;
    private boolean mouseReady;
    private int timer = 50;
    private Vector savedMousePos = new Vector(-1, -1);
    /**
     * Creates the pop up box with text, width, and height.
     * @param t         A SuperTextBox that fades in when hovered over
     * @param width     Width of the area that triggers the box when hovered over
     * @param height    Height of the area that triggers the box when hovered over   
     */
    public PopUpBox(SuperTextBox t, int width, int height){
        GreenfootImage img = new GreenfootImage(width, height);
        //img.setColor(Color.BLUE);
        //img.fill();
        setImage(img);
        text = t;
        t.getImage().setTransparency(0);
        
        fadeIn = false;
    }
    public void addedToWorld(World w){
        w.addObject(text, getX(), getY() - getImage().getHeight());
        
    }
    public void act()
    {
        
        // Checks whether the text box should fade in   
        if(fadeIn){
            
            
            fadeIn(); // fade slowly
        }
        else if(timer <= 0){
            // when the cursor timer is done, begin fading phase
            fadeIn = true;
        }
        // This statement checks if the previous mouse position saved when the cursor is initially hovered over is the same
        // Essentially, checks if the mouse has moved, if it has not we can decrement the timer
        else if(Mouse.getPosition() != null && savedMousePos.getX() == Mouse.getPosition().getX() && savedMousePos.getY() == Mouse.getPosition().getY()){
            timer--;
        
        }
        // Checks if the cursor is over the trigger zone
        else if(Mouse.getHoveredActor(PopUpBox.class) == this){
            // reset the timer
            timer = 50;
            // save the current mouse pos
            savedMousePos = Mouse.getPosition();
            
        }
        else{
            // if out the trigger zone, fade out the text box
            fadeOut();
        }
        

    }
    /**
     * A helper method to remove all associated objects and itself
     */
    public void removeObject(){
        getWorld().removeObject(text);
        getWorld().removeObject(this);
    }
    /**
     * Used to fade in the text box when triggered
     */
    private void fadeIn(){
        GreenfootImage img = text.getImage();
        img.setTransparency(Utility.clamp(img.getTransparency() +  25, 0, 255));
        if(img.getTransparency()  == 255){
            fadeIn = false;
            timer = 50;
        }
    }
    /**
     * Used to fade out the text box when not triggered
     */
    private void fadeOut(){
        GreenfootImage img = text.getImage();
        img.setTransparency(Utility.clamp(img.getTransparency() -  25, 0, 255));
        if(img.getTransparency()  == 0){
            fadeOut = false;
            timer = 50;
        }
    }
}
