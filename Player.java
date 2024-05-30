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
    
    private int speed;

    public Player() {
        super(Team.ALLY, 100);
        speed = 5;
        hp = 100;
    }

    public void act() {
        movePlayer();
        // Add other behaviours here (like checking for collisions, etc.)
    }

    private void movePlayer() {
        if (Greenfoot.isKeyDown("w")) {
            setLocation(getX(), getY() - speed);
        }
        if (Greenfoot.isKeyDown("s")) {
            setLocation(getX(), getY() + speed);
        }
        if (Greenfoot.isKeyDown("a")) {
            setLocation(getX() - speed, getY());
        }
        if (Greenfoot.isKeyDown("d")) {
            setLocation(getX() + speed, getY());
        }
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
}

