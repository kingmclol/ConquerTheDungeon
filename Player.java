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
    
    private int frame = 0, acts = 0;
    private static int x, y; // location of the Player.
    private Animation right,left,down,up;
    private boolean isShooting;
    private boolean isSlashing;
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
        up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
        down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 3);
        setImage(up.getFrame(0));
    }

    public void act() {
        movePlayer();
        checkPowerUpStatus();
        handleShooting();
        attack(10);
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
        }
        if (Greenfoot.isKeyDown("s")) {
            setLocation(getX(), getY() + speed);
            setImage(down.getFrame(frame));
        }
        if (Greenfoot.isKeyDown("a")) {
            setLocation(getX() - speed, getY());
            setImage(left.getFrame(frame));
        }
        if (Greenfoot.isKeyDown("d")) {
            setLocation(getX() + speed, getY());
            setImage(right.getFrame(frame));
        }
        x = getX();
        y = getY();
        
        
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

    private void attack(int damage){
        List<Enemy> enemies = getObjectsInRange(30, Enemy.class);
        for (Enemy enemy : enemies) {
            enemy.takeDamage(damage);
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
}

