import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class AttackUpgrade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
