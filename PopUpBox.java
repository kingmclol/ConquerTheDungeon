import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class PopUpBox extends UI
{
    private SuperTextBox text;
    private boolean fadeIn;
    private boolean fadeOut;
    private boolean mouseReady;
    private int timer = 50;
    private Vector savedMousePos = new Vector(-1, -1);
    public PopUpBox(SuperTextBox t, int width, int height){
        GreenfootImage img = new GreenfootImage(width, height);
        //img.setColor(Color.BLUE);
        //img.fill();
        setImage(img);
        text = t;
        t.getImage().setTransparency(0);
        
        fadeIn = false;
        fadeOut = false;
    }
    public void addedToWorld(World w){
        w.addObject(text, getX(), getY() - getImage().getHeight());
        
    }
    public void act()
    {
       
            if(fadeIn){
                

                fadeIn();
            }
            else if(timer <= 0){
                
                fadeIn = true;
            }
            else if(Mouse.getPosition() != null && savedMousePos.getX() == Mouse.getPosition().getX() && savedMousePos.getY() == Mouse.getPosition().getY()){
                timer--;
            
            }
            else if(Mouse.getHoveredActor(PopUpBox.class) == this){
                timer = 50;
                savedMousePos = Mouse.getPosition();
                
            }
            else{
                
                fadeOut();
            }
        

    }
    public void removeObject(){
        getWorld().removeObject(text);
        getWorld().removeObject(this);
    }
    private void fadeIn(){
        GreenfootImage img = text.getImage();
        img.setTransparency(Utility.clamp(img.getTransparency() +  25, 0, 255));
        if(img.getTransparency()  == 255){
            fadeIn = false;
            timer = 50;
        }
    }
    private void fadeOut(){
        GreenfootImage img = text.getImage();
        img.setTransparency(Utility.clamp(img.getTransparency() -  25, 0, 255));
        if(img.getTransparency()  == 0){
            fadeOut = false;
            timer = 50;
        }
    }
}
