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
    
    private int speed, frame = 0, acts = 0;
    private static int x, y; // location of the Player.
    private Animation right,left,down,up;

    public Player() {
        super(Team.ALLY, 100);
        speed = 5;
        hp = 100;
        up = Animation.createAnimation(new GreenfootImage("Player.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Player.png"), 9, 1, 9, 64, 64);
        down = Animation.createAnimation(new GreenfootImage("Player.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Player.png"), 11, 1, 9, 64, 64, 3);
        setImage(up.getFrame(0));
    }

    public void act() {
        movePlayer();
        // Add other behaviours here (like checking for collisions, etc.)
        attack(10);
    }

    private void movePlayer() {
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

    private void attack(int damage){
        List<Enemy> enemies = getObjectsInRange(30, Enemy.class);
        for (Enemy enemy : enemies) {
            enemy.takeDamage(damage);
        }
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

