import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SuperWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SuperWorld extends World
{

    /**
     * Constructor for objects of class SuperWorld.
     * 
     */
    public SuperWorld()
    {    
        super(1200, 768, 1, false);  
        Mouse.setWorld(this);
    }
    public void act() {
        Timer.tick();
        Mouse.getMouse();
        Keyboard.update();
    }
    /**
     * Displays some text alert on the screen.
     * @param str The text to display
     * @param c the color of the text.
     */
    public void alert(String str, Color c) {
        alert(str, c, 100);
    }
    /**
     * Displays some text alert on the screen at your chosen y-level.
     * @param str The text to display
     * @param c the text color.
     * @param yLevel the y position in pixels it should appear.
     */
    public void alert(String str, Color c, int yLevel) {
        TextBox info = new TextBox(str, 36, c, null, 2, 255);
        addObject(info, getWidth()/2, yLevel);
        info.fadeOut();
    }
}
