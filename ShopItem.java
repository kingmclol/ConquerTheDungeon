import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * <p>A shop item is an upgrade costing coins for the player when it reaches a shop level in the game.</p>
 * <p>There are 3 types of shop items: HP upgrades, speed upgrades, and attack damage upgrades.</p>
 * 
 * <p>When the player clicks or presses E near the item, the upgrade is bought.</p>
 * @author Neelan Thurairajah
 * @version June 2024
 */
public abstract class ShopItem extends SuperSmoothMover
{
    protected PopUpBox itemInfo;
    protected boolean canInteract;
    /**
     * Creates a shop item with text that contains info for a tooltip
     */
    public ShopItem(String [] text){
        itemInfo = new PopUpBox(new SuperTextBox(text, new Font(15), 275), 64, 64);
    }
    public void addedToWorld(World w){
        w.addObject(itemInfo, getX(), getY());
    }
    public void act()
    {
        // Check if the player is within 100 pixels of the shop item
        ArrayList<Player> player = (ArrayList<Player>) getObjectsInRange(90, Player.class);
        if(player.size() > 0){
            // if there is a player
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
