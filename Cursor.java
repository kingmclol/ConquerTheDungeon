import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Cursor extends Actor
{
    public Cursor() {
        GreenfootImage image = new GreenfootImage("cursor.png");
        int newWidth = image.getWidth() / 10;
        int newHeight = image.getHeight() / 10;
        image.scale(newWidth, newHeight);
        setImage(image);  
    }
    
    public void act()
    {
        // Follow the mouse
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            setLocation(mouse.getX(), mouse.getY());
        }
    }
}
