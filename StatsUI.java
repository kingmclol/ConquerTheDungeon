import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StatsUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public  class StatsUI extends UI
{
    private GreenfootImage img;
    private static LoadBar hpBar = new LoadBar(75, 10, Color.RED, true);
    private static LoadBar cdBar1 = new LoadBar(100, 4, Color.WHITE, Color.RED, false);
    private static LoadBar cdBar2 = new LoadBar(100, 4, Color.WHITE, Color.GREEN, false);
    
    private Picture charImg = new Picture("charportrait.png");
    private Picture staffImg = new Picture("staff.png");
    private Picture swordImg = new Picture("swordicon.png");
    private Picture heartImg = new Picture("hearticon.png");
    //private Picture coinImg = new Picture("coin.png");
    private Picture speedImg = new Picture("speedicon.png");
    
    private PopUpBox atkStatInfo = new PopUpBox(new SuperTextBox(new String []{"- Strength Stat -", "Determines the ",  "attack damage you","deal to enemies."}, new Font(13), 120), 55, 75);
    private PopUpBox hpStatInfo = new PopUpBox(new SuperTextBox(new String []{"- Health Stat -", "Determines ",  "the hit points", "you have."}, new Font(13), 97), 55, 75);
    private PopUpBox spdStatInfo = new PopUpBox(new SuperTextBox(new String []{"- Speed Stat -", "Determines how ",  "fast you move."}, new Font(13), 120), 55, 75);
    
    private PopUpBox staffInfo = new PopUpBox(new SuperTextBox(new String []{"- Staff Meter -", "This meter determines",  "how long you can"," use the staff."}, new Font(13), 135), 70, 35);

    private static TextBox atkText;
    private static TextBox heartText; 
    private static TextBox spdText; 
    private static TextBox coinText;
    
    private CoinAnim coinAnim = new CoinAnim();
    //private TextBox atkText = new TextBox(Integer.toString(attackDmg), 24, Color.WHITE, null, 0, 100);

    public StatsUI(Player player) {
        img = new GreenfootImage("blankui.png");
        atkText = new TextBox(Integer.toString(player.getAttackDmg()), 24, Color.WHITE, null, 0, 255);
        heartText = new TextBox(Integer.toString(player.getMaxHp()), 24, Color.WHITE, null, 0, 255);
        spdText = new TextBox(Integer.toString(player.getMaxHp()), 24, Color.WHITE, null, 0, 255);
        coinText = new TextBox(Integer.toString(player.getCoin()), 24, Color.YELLOW, null, 0, 255);
        updateCoin(player.getCoin());
        updateAtkDmg(player.getAttackDmg());
        updateHP(((double) player.getHp()/player.getMaxHp())*100.0);
        updateSpd(player.getSpeed());
        cdBar1.setTargetPercent(100.0);
        cdBar2.setTargetPercent(100.0);
        setImage(img);
    }
    public static void loadStatsData(String [] data){
        hpBar.setTargetPercent(((double)Integer.valueOf(data[4])/ Integer.valueOf(data[3]))*100.0);
        cdBar1.setTargetPercent(100.0);
        cdBar2.setTargetPercent(100.0);
        coinText.display(data[5]);
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
    public static void updateCoin(int coin){
        coinText.display(Integer.toString(coin));
    }
    public static void updateAtkDmg(int dmg){
        atkText.display(Integer.toString(dmg));       
    }
    public static void updateMaxHp(int hp){
        heartText.display(Integer.toString(hp));        
    }
    public static void updateSpd(int spd){
        spdText.display(Integer.toString(spd));         
    }
    public static void updateHP(double percent){
        hpBar.setTargetPercent(percent);
    }
    public static void updateCd1(double percent){
        cdBar1.setTargetPercent(percent);
    }
    public static void updateCd2(double percent){
        cdBar2.setTargetPercent(percent);
    }
}
