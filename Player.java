import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Entity
{
    private boolean isPoweredUp = false;
    private long powerUpStartTime = 0;
    private int normalSpeed;
    private int powerUpSpeed;
    private int normalShootingInterval;
    private int powerUpShootingInterval;
    private int shootingTimer;

    private int speed, atkSpd = 10, frame = 0, acts = 0, index = 0;
    private static int x, y; // location of the Player.
    //Cooldowns, durations:
    private double timeForStaff = 600.0, remainingCds = 0;
    //Moving
    private Animation right,left,down,up, staffUp, staffDown, staffLeft, staffRight;
    private GreenfootImage[] swingingUp = new GreenfootImage[6],swingingDown = new GreenfootImage[6],swingingLeft = new GreenfootImage[6],swingingRight = new GreenfootImage[6];
    private static String facing = "right",weapon = "sword";
    private boolean inAttack = false, mouseClick, dealtDamage = false;
    private static String[] weaponList = new String[2];

    private SuperStatBar hpBar;
    public Player() {
        super(Team.ALLY, 100);
        normalSpeed = 5;
        powerUpSpeed = 8;
        normalShootingInterval = 50;
        powerUpShootingInterval = 30;
        shootingTimer = 0;
        hp = 100;
        hpBar = new SuperStatBar(hp, hp, this, 50, 8, -33, Color.GREEN, Color.BLACK, false, Color.YELLOW, 1);
        //Animation spritesheet cutter using Mr Cohen's animation class: 
        up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
        down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 10);

        //Initialize weapons
        weaponList[0] = "sword";
        weaponList[1] = "staff";

        //weapon:
        for(int i = 0; i<swingingUp.length; i++)
        {
            swingingUp[i] = new GreenfootImage("sword/up" + (i+1) + ".png");
            swingingLeft[i] = new GreenfootImage("sword/left" + (i+1) + ".png");
            swingingDown[i] = new GreenfootImage("sword/down" + (i+1) + ".png");
            swingingRight[i] = new GreenfootImage("sword/right" + (i+1) + ".png");
        }

        //Start at frame 0
        setImage(up.getFrame(0));
    }

    public void act() {
        if(remainingCds != 0) // 1 minute
        {
            remainingCds--;
        }
        else
        {
            String key = Greenfoot.getKey();
            if("r".equals(key))
            {
                switchWeapon();
            }
        }
        if(this.getCurrentWeapon().equals("staff"))
        {
            timeForStaff--;
            // continue timer, up to 10 seconds per time whether you end early or not.
        }
        else if(timeForStaff < 600 && this.getCurrentWeapon().equals("sword"))
        {
            timeForStaff = timeForStaff+(600/remainingCds); 
            // for every second spent in sword, regenerate 1/6th of the timer second for staff.
        }
        //Mouse click == false prevents spam clicking, which keeps resetting the animation.
        if(Greenfoot.mousePressed(null) && mouseClick == false)
        {
            inAttack = true;
            mouseClick = true;
            frame = 0;
            //set frame 0 when attacking.
        }
        if(!inAttack)
        {
            movePlayer();
        }
        else
        {
            attackAnimation();
            if(this.getCurrentWeapon().equals("staff"))
            {
                handleShooting();
            }
        }
        // Add other behaviours here (like checking for collisions, etc.)
        checkPowerUpStatus();
        attack(10);
        // if still in staff and not middle of attack animation,
        if(timeForStaff < 0 && !inAttack)
        {
            switchWeapon(); // automatically switch
            remainingCds = 3600; // restart Cooldown once staff is expired.
            timeForStaff = 600; // reset Timer
        }
        acts++;
    }

    public void addedToWorld(World world) {
        super.addedToWorld(world);
        world.addObject(hpBar, getX(), getY() - 33); // Position the HP bar slightly above the player
    }

    private void movePlayer() {
        int speed = isPoweredUp ? powerUpSpeed : normalSpeed;
        if(acts%(atkSpd-powerUpSpeed/2) == 0)
        {
            frame = (frame+1)%(up.getAnimationLength()); 
        }
        acts++;
        if (Greenfoot.isKeyDown("w")) {
            setLocation(getX(), getY() - speed);
            setImage(up.getFrame(frame));
            facing = "up";
        }
        if (Greenfoot.isKeyDown("s")) {
            setLocation(getX(), getY() + speed);
            setImage(down.getFrame(frame));
            facing = "down";
        }
        if (Greenfoot.isKeyDown("a")) {
            setLocation(getX() - speed, getY());
            setImage(left.getFrame(frame));
            facing = "left";
        }
        if (Greenfoot.isKeyDown("d")) {
            setLocation(getX() + speed, getY());
            setImage(right.getFrame(frame));
            facing = "right";
        }
        x = getX();
        y = getY();
        if(acts % 2 == 0)
        {
            frame = (frame+1)%(right.getAnimationLength());
        }
    }

    private void handleShooting(){
        shootingTimer++;
        int shootingInterval = isPoweredUp ? powerUpShootingInterval : normalShootingInterval;
        if (Greenfoot.mouseClicked(null) && shootingTimer >= shootingInterval) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                shoot(mouseX, mouseY);
                shootingTimer = 0;
            }
        }
    }

    private void attack(int damage) {
        if(inAttack && !dealtDamage)
        {
            //Implement CollisionBox
            List<Enemy> enemies = getObjectsInRange(30, Enemy.class);
            for (Enemy enemy : enemies) {
                if(frame == 5) // So it doesn't appear like it died before sword hits.
                {
                    enemy.takeDamage(damage);
                    dealtDamage = true; 
                }
            } 
        }
    }

    private void shoot(int targetX, int targetY) {
        Bullet bullet = new Bullet(2, 20, this,targetX, targetY);
        getWorld().addObject(bullet, getX(), getY());
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            die();
        }
    }

    public void heal(int healAmount) {
        hp += healAmount;
        if (hp > 100) {
            hp = 100; // Assuming max HP is 100
        }
    }

    public void activatePowerUp() {
        isPoweredUp = true;
        powerUpStartTime = System.currentTimeMillis();
    }

    private void checkPowerUpStatus() {
        if (isPoweredUp && (System.currentTimeMillis() - powerUpStartTime >= 8000)) {
            isPoweredUp = false;
        }
    }

    public void die() {
        getWorld().removeObject(this);
    }

    public int getHp() {
        return hp;
    }

    public static int returnX()
    {
        return x;
    }

    public static int returnY()
    {
        return y;
    }

    public void idle()
    {

    }

    public String getCurrentWeapon()
    {
        return weapon;
    }

    public void switchWeapon()
    {
        index++;
        frame = 0;
        String nextWeapon = weaponList[(index%weaponList.length)];
        weapon = nextWeapon;
        if(weapon.equals("sword"))
        {
            up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
            down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 10);
            switch(facing)
            {
                case "up":
                    setImage(up.getFrame(frame));
                    break;
                case "down":
                    setImage(down.getFrame(frame));
                    break;
                case "right":
                    setImage(right.getFrame(frame));
                    break;
                case "left":
                    setImage(left.getFrame(frame));
                    break;
            }
        }
        else if(weapon.equals("staff"))
        {
            up = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 8, 1, 9, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 9, 1, 9, 64, 64);
            down = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 10, 1, 9, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 11, 1, 9, 64, 64, 10);
            switch(facing)
            {
                case "up":
                    setImage(up.getFrame(frame));
                    break;
                case "down":
                    setImage(down.getFrame(frame));
                    break;
                case "right":
                    setImage(right.getFrame(frame));
                    break;
                case "left":
                    setImage(left.getFrame(frame));
                    break;
            }
            staffUp = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 4, 1, 8, 64, 64);
            staffLeft = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 5, 1, 8, 64, 64);
            staffDown = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 6, 1, 8, 64, 64);
            staffRight = Animation.createAnimation(new GreenfootImage("PlayerStaff.png"), 7, 1, 8, 64, 64,7);
        }
    }

    /**
     * 
     */
    public void attackAnimation()
    {
        switch(weapon)
        {
            case "sword": // if holding sword
                if(frame == (swingingUp.length-1)) // if animation reaches the end.
                {
                    inAttack = false;
                    frame = 0;
                    switch(facing)
                    {
                        case "up":
                            setImage(up.getFrame(frame));
                            break;
                        case "down":
                            setImage(down.getFrame(frame));
                            break;
                        case "right":
                            setImage(right.getFrame(frame));
                            break;
                        case "left":
                            setImage(left.getFrame(frame));
                            break;
                    }
                    mouseClick = false;
                    dealtDamage = false;
                    break;
                }
                switch(facing)
                {
                    case "up":
                        setImage(swingingUp[frame]);
                        break;
                    case "down":
                        setImage(swingingDown[frame]);
                        break;
                    case "left":
                        setImage(swingingLeft[frame]);
                        break;
                    case "right":
                        setImage(swingingRight[frame]);
                        break;
                }
                if(acts % atkSpd == 0)
                {
                    frame = (frame+1)%(swingingRight.length);
                }
                break;
            case "staff":
                if(frame == (staffUp.getAnimationLength()-1)) // if animation reaches the end.
                {
                    inAttack = false;
                    frame = 0;
                    switch(facing)
                    {
                        case "up":
                            setImage(up.getFrame(frame));
                            break;
                        case "down":
                            setImage(down.getFrame(frame));
                            break;
                        case "right":
                            setImage(right.getFrame(frame));
                            break;
                        case "left":
                            setImage(left.getFrame(frame));
                            break;
                    }
                    mouseClick = false;
                    break;
                }
                switch(facing)
                {
                    case "up":
                        setImage(staffUp.getFrame(frame));
                        break;
                    case "down":
                        setImage(staffDown.getFrame(frame));
                        break;
                    case "left":
                        setImage(staffLeft.getFrame(frame));
                        break;
                    case "right":
                        setImage(staffRight.getFrame(frame));
                        break;
                }
                if(acts % atkSpd == 0)
                {
                    frame = (frame+1)%(staffUp.getAnimationLength());
                }
                break;
            case "bow":
                break;
        }
    }
}

