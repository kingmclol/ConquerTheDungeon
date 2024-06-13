import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Speed upgrade that has 3 tiers (unimplemented, 1 tier currently).
 * <p>When the player has enough coins, they can gain +1 movement speed at a cost of 20 coins.
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class SpeedUpgrade extends ShopItem
{
    private static String[] info = {"- UPGRADE - ", "Gain +1 movement speed permanantly", "Cost: 20 coins"};
    private GreenfootImage img = new GreenfootImage("speedicon.png");
    private int tier;
    private int price = 20;
    public SpeedUpgrade(int tier){
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
            player.removeCoin(price);
            if(tier == 1){
                
                player.addSpeed(1);
            }
            if(tier == 2){
                
                player.addSpeed(1);
            }
            if(tier == 3){
                
                player.addSpeed(3);
            }
            return true;
        }
        return false;
    }
}
