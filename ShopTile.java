import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShopTile here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShopTile extends Obstruction
{
    public static GreenfootImage img = new GreenfootImage("wall.jpg");
    private ShopItem s;
    public ShopTile(){
        super(img);
        int rand = Greenfoot.getRandomNumber(3);
        
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

    public void act()
    {
        // Add your action code here.
    }
    public String getID() {
        return "shp";
    }
}
