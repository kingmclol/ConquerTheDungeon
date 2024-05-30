import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Enemy extends Actor {
    private int hp;

    public Enemy() {
        this.hp = 50;  
    }

    public void act() {
        
    }

    public void takeDamage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            die();
        }
    }

    private void die() {
        getWorld().removeObject(this);
    }
}

