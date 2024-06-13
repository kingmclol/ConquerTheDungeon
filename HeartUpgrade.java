import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Health upgrade that has 3 tiers (unimplemented, 1 tier currently).
 * <p>When the player has enough coins, they can gain +10 max HP at a cost of 10 coins.
 * @author Neelan Thurairajah 
 * @version June 2024
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
