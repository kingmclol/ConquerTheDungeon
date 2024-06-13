import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Attack damage upgrade that has 3 tiers (unimplemented, 1 tier currently).
 * <p>When the player has enough coins, they can gain +5 attack damages at a cost of 10 coins.
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class AttackUpgrade extends ShopItem
{
    private static String[] info = {"- UPGRADE - ", "Gain +5 attack damage permanantly", "Cost: 10 coins"};
    private GreenfootImage img = new GreenfootImage("swordicon.png");
    private int tier;
    private int price = 10;
    public AttackUpgrade(int tier){
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

                player.addAttackDamage(2);
            }
            if(tier == 2){

                player.addAttackDamage(5);
            }
            if(tier == 3){

                player.addAttackDamage(8);
            }         
            player.removeCoin(price);
            return true;
        }
        return false;
    }
}
