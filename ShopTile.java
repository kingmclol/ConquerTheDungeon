import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A shop tile holds a shop item for player upgrades.
 * 
 * @author Neelan Thurairajah
 * @version June 2024
 */
public class ShopTile extends Obstruction
{
    public static GreenfootImage img = new GreenfootImage("wall.jpg");
    private ShopItem s;
    /**
     * Creates a shop tile with a random shop item that could either be: Heart, Speed, or Attack
     */
    public ShopTile(){
        super(img);
        int rand = Greenfoot.getRandomNumber(3);
        // Create random upgrade
        if(rand == 0){
            s = new HeartUpgrade(2);
        }
        else if (rand == 1) {
            s = new SpeedUpgrade(2);
        }
        else{
            s = new AttackUpgrade(2);
        }
        
    }
    public void addedToWorld(World w){
        w.addObject(AABB, 0, 0);
        w.addObject(s, getX(), getY());
    }


    public String getID() {
        return "shp";
    }
}
