import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class EndScreenWorld extends SuperWorld
{
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
        //titleAnchor = new Vector(getWidth()/2, 80);
        addObject(title, getWidth()/2, 280);
        TextBox title2 = new TextBox("LEVEL: " + GameData.getLevel(), 86, Color.RED, null, 2, 0); 
        addObject(title2, getWidth()/2, 380);
        
    }
}



