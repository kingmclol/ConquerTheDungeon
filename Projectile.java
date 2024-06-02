import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.List;

public abstract class Projectile extends SuperSmoothMover {
    private int spawnOffset = 10;
    private int rotation;
    private int speed;
    private int damage;
    private Actor owner;
    private boolean hasHit = false; // Flag to track if the projectile has already hit something

    public Projectile(int spd, int dmg, Actor own) {
        speed = spd;
        damage = dmg;
        owner = own;
    }

    public void act() {
        if (!hasHit) { // Only do damage if the projectile has not yet hit something
            doDamage();
        }
        move(speed);
        checkEdge();
    }

    public void doDamage() {
        Actor a = getOneIntersectingObject(Actor.class);

        if (a == null || a == owner) {
            return;
        }
        if (a instanceof Player) {
            ((Player) a).damage(damage);
            hasHit = true; // Set the flag to true once damage is dealt
            getWorld().removeObject(this); // Remove the projectile from the world
        } else if (a instanceof Enemy) {
            ((Enemy) a).damage(damage);
            hasHit = true; // Set the flag to true once damage is dealt
            getWorld().removeObject(this); // Remove the projectile from the world
        }
    }

    private void checkEdge() {
        if (isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
}

