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
    
    private int speed, frame = 0, acts = 0, index = 0;
    private static int x, y; // location of the Player.
    //Moving
    private Animation right,left,down,up;
    private GreenfootImage[] swingingUp = new GreenfootImage[6],swingingDown = new GreenfootImage[6],swingingLeft = new GreenfootImage[6],swingingRight = new GreenfootImage[6];
    private String facing = "right",weapon = "sword";
    private boolean inAttack = false, isShooting, isSlashing;
    private String[] weaponList = new String[2];

    public Player() {
        super(Team.ALLY, 100);
        isShooting = false;
        isSlashing = false;
        normalSpeed = 5;
        powerUpSpeed = 8;
        normalShootingInterval = 50;
        powerUpShootingInterval = 30;
        shootingTimer = 0;
        hp = 100;
        
        //Animation spritesheet cutter using Mr Cohen's animation class: 
        up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
        down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 3);
        
        //Initialize weapons
        weaponList[0] = "sword";
        weaponList[1] = "staff";
        
        //sword:
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
        
        if(Greenfoot.mousePressed(null))
        {
            inAttack = true;
            frame = 0;
        }
        if(!inAttack)
        {
            movePlayer();
        }
        // Add other behaviours here (like checking for collisions, etc.)
        checkPowerUpStatus();
        handleShooting();
        attack(10);
        acts++;
    }

    private void movePlayer() {
        int speed = isPoweredUp ? powerUpSpeed : normalSpeed;
        if(acts%2 == 0)
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
        if(inAttack)
        {
            List<Enemy> enemies = getObjectsInRange(30, Enemy.class);
            for (Enemy enemy : enemies) {
                enemy.takeDamage(damage);
            }
            attackAnimation();  
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

    private void die() {
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
    public void switchWeapon()
    {
        if(Greenfoot.isKeyDown("e"))
        {
            index++;
            weapon = weaponList[(index%weaponList.length)];
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
                
                break;
            case "staff":
                break;
            case "bow":
                break;
        }
        if(acts % 2 == 0)
        {
            frame = (frame+1)%(swingingUp.length);
        }
    }
}

