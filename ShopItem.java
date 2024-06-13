import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class ShopItem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class ShopItem extends SuperSmoothMover
{
    protected PopUpBox itemInfo;
    protected boolean canInteract;
    public ShopItem(String [] text){
        itemInfo = new PopUpBox(new SuperTextBox(text, new Font(15), 275), 64, 64);
    }
    public void addedToWorld(World w){
        w.addObject(itemInfo, getX(), getY());
    }
    public void act()
    {
        
        ArrayList<Player> player = (ArrayList<Player>) getObjectsInRange(100, Player.class);
        if(player.size() > 0){
            canInteract = true;
            
        }
        else {
            canInteract = false;
        }
        if(canInteract){
            String key = Keyboard.getCurrentKey();
            System.out.println(Mouse.hoveringOver(this));
            if("e".equals(key) || (Greenfoot.mousePressed(null) && Mouse.hoveringOver(this))){
                if(addUpgrade())removeObject();
                
            }
        }

    }
    protected void removeObject(){
        itemInfo.removeObject();
        getWorld().removeObject(this);
    }
    protected abstract boolean  addUpgrade();
}
