import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HeartUpgrade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HeartUpgrade extends ShopItem
{
    private static String[] info = {"- UPGRADE - ", "Gain +10 max hit points permanantly", "Restore HP to max", "Cost: 10 coins"};
    private GreenfootImage img = new GreenfootImage("hearticon.png");
    private int tier;
    private int price = 10;
    public HeartUpgrade(int tier){
        super(info);
        this.tier = tier;
        setImage(img);
    }
    public void act()
    {
        super.act();
    }
    public boolean addUpgrade(){
        Player player = GameData.getPlayer();

        if(player.getCoin() >= price) {
            if(tier == 1){
            
                player.addMaxHp(5);
            }
            if(tier == 2){
                
                player.addMaxHp(10);
            }
            if(tier == 3){
                
                player.addMaxHp(15);
            }
            player.heal(player.getMaxHp());
            player.removeCoin(price);
            return true;
        }
        return false;
    }
}
