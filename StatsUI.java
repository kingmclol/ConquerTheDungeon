import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The Stats User Interface displays all the player stats in a graphic on the right of the screen during gameplay.
 * There are 3 stats displayed: Attack Damage, Max Health, and Speed
 * And there are 3 abilities and their cooldowns displayed: Ultimate, Dash, and Staff 
 * Each time an upgrade to stats happens to the player, the stats interface is updated accordingly.
 * @author Neelan Thurairajah
 * @version June 2024
 */
public  class StatsUI extends UI
{
    // Intialize image, cd bars and hp bar variables
    private GreenfootImage img;
    private static LoadBar hpBar = new LoadBar(75, 10, Color.RED, 255, true);
    private static LoadBar cdBar1 = new LoadBar(100, 4, Color.WHITE, Color.RED, 255, false);
    private static LoadBar cdBar2 = new LoadBar(100, 4, Color.WHITE, Color.GREEN, 255, false);
    private static LoadBar ultBar = new LoadBar(60, 60, Color.GRAY, Color.GRAY, 200, false);
    // Images for the icons of stats
    private static Picture ult1 = new Picture("ult1.png");
    private static Picture ult2 = new Picture("ult2.png");
    private Picture charImg = new Picture("charportrait.png");
    private Picture staffImg = new Picture("staff.png");
    private Picture swordImg = new Picture("swordicon.png");
    private Picture heartImg = new Picture("hearticon.png");
    private Picture speedImg = new Picture("speedicon.png");
    // Tooltips for info about stats and abilities
    private PopUpBox atkStatInfo = new PopUpBox(new SuperTextBox(new String []{"- Strength Stat -", "Determines the ",  "attack damage you","deal to enemies."}, new Font(13), 120), 55, 75);
    private PopUpBox hpStatInfo = new PopUpBox(new SuperTextBox(new String []{"- Health Stat -", "Determines ",  "the hit points", "you have."}, new Font(13), 97), 55, 75);
    private PopUpBox spdStatInfo = new PopUpBox(new SuperTextBox(new String []{"- Speed Stat -", "Determines how ",  "fast you move."}, new Font(13), 120), 55, 75);
    
    private PopUpBox staffInfo = new PopUpBox(new SuperTextBox(new String []{"- Staff Meter -", "This meter determines",  "how long you can"," use the staff."}, new Font(13), 135), 70, 35);
    private PopUpBox ultInfo = new PopUpBox(new SuperTextBox(new String []{"- Ultimate Ability -", "Activate by pressing Q",  "to use the special ability","of the current weapon."}, new Font(13), 160), 65, 65);
    // Text boxes with stat values
    private static TextBox atkText;
    private static TextBox heartText; 
    private static TextBox spdText; 
    private static TextBox coinText;
    // Animation for coin icon
    private CoinAnim coinAnim = new CoinAnim();
    //private TextBox atkText = new TextBox(Integer.toString(attackDmg), 24, Color.WHITE, null, 0, 100);
    /**
     * Creates a Stat User Interface with stats retrieved from the player object
     * @param player    A player object that is used to get stat values from
     */
    
    public StatsUI(Player player) {
        img = new GreenfootImage("blankui.png");
        atkText = new TextBox(Integer.toString(player.getAttackDmg()), 24, Color.WHITE, null, 0, 255);
        heartText = new TextBox(Integer.toString(player.getMaxHp()), 24, Color.WHITE, null, 0, 255);
        spdText = new TextBox(Integer.toString(player.getMaxHp()), 24, Color.WHITE, null, 0, 255);
        coinText = new TextBox(Integer.toString(player.getCoin()), 24, Color.YELLOW, null, 0, 255);
        ult1 = new Picture("ult1.png");
        ult2 = new Picture("ult2.png");
        ultBar.setRotation(-90);
        updateCoin(player.getCoin());
        updateAtkDmg(player.getAttackDmg());
        updateHP(((double) player.getHp()/player.getMaxHp())*100.0);
        updateSpd(player.getSpeed());
        cdBar1.setTargetPercent(100.0);
        cdBar2.setTargetPercent(100.0);
        ultBar.setTargetPercent(0);
        switchUlt(player.getCurrentWeapon());
        setImage(img);
    }

    public void addedToWorld(World w){
        w.addObject(hpBar, getX(), 130);
        w.addObject(coinAnim, getX() - 10, 170);
        w.addObject(coinText, getX() + 10, 170);
        w.addObject(swordImg, getX() - 40, 240);
        w.addObject(atkText, getX() - 40, 280);
        w.addObject(heartImg, getX() + 40, 240);
        w.addObject(heartText, getX() + 40, 280);
        w.addObject(speedImg, getX() - 40, 340);
        w.addObject(spdText, getX() - 40, 380);
        w.addObject(charImg, getX(), 75);
        w.addObject(cdBar1, getX(), 700);
        w.addObject(cdBar2, getX(), 625);
        w.addObject(ult1, getX(), 540);
        w.addObject(ult2, getX(), 540);
        w.addObject(ultInfo, getX(), 540);
        w.addObject(ultBar, getX(), 540);
        w.addObject(staffImg, getX() - 50, 680);
        w.addObject(staffInfo, getX() - 50, 680);
        w.addObject(atkStatInfo, getX() - 40, 250);
        w.addObject(hpStatInfo, getX() + 40, 250);
        w.addObject(spdStatInfo, getX() - 40, 350);
    }
    public void act()
    {
        // Add your action code here.
    }
    /**
     * Updates coin display
     * @param coin  Number of coins to display 
     */
    public static void updateCoin(int coin){
        coinText.display(Integer.toString(coin));
    }
    /**
     * Updates attack damage display
     * @param dmg  The attack damage number to display
     */
    public static void updateAtkDmg(int dmg){
        atkText.display(Integer.toString(dmg));       
    }
    /**
     * Updates max HP display
     * @param dmg  The max HP number to display
     */
    public static void updateMaxHp(int hp){
        heartText.display(Integer.toString(hp));        
    }
    /**
     * Updates speed display
     * @param speed The speed number to display
     */
    public static void updateSpd(int spd){
        spdText.display(Integer.toString(spd));         
    }
    /**
     * Updates current HP on HP bar
     * @param percent   The percentage of HP remaining 
     */
    public static void updateHP(double percent){
        hpBar.setTargetPercent(percent);
    }
    /**
     * Updates the staff bar
     * @param percent   The percentage of time left on the staff 
     */
    public static void updateCd1(double percent){
        cdBar1.setTargetPercent(percent);
    }
    /**
     * Updates the dash cooldown bar
     * @param percent   The percentage of cooldown left on the dash 
     */
    public static void updateCd2(double percent){
        cdBar2.setTargetPercent(percent);
    }
    /**
     * Updates the ultimate cooldown bar
     * @param percent   The percentage of time left on the ultimate cooldown 
     */
    public static void updateUlt(double percent){
        ultBar.setTargetPercent(percent);
    }
    /**
     * Switches the ultimate icon image based on the current weapon
     * @param weapon   Current weapon used by the player 
     */
    public static void switchUlt(String weapon){
        if(weapon.equals("sword")){
            ult1.setTransparency(0);
            ult2.setTransparency(255);
        }
        else{
            ult1.setTransparency(255);
            ult2.setTransparency(0);
        }
    }
}
