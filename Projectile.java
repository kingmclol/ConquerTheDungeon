import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.List;

public abstract class Projectile extends SuperSmoothMover {
    private int spawnOffset = 10;
    private int rotation;
    protected int speed;
    protected int damage;
    protected Actor owner;
    private boolean hasHit = false; // Flag to track if the projectile has already hit something

    public Projectile(int spd, int dmg, Actor own) {
        speed = spd;
        damage = dmg;
        owner = own;
    }

    public void act() {
        move(speed);
        checkEdgeOrWall();
        if (!hasHit && getWorld() != null) { // Only do damage if the projectile has not yet hit something
            doDamage();
        }
    }

    public void doDamage() {
        Damageable a = (Damageable)getOneIntersectingObject(Damageable.class);

        if (a == null || a == owner) {
            return;
        }

        a.damage(damage);
        hasHit = true; // Set the flag to true once damage is dealt
        getWorld().removeObject(this); // Remove the projectile from the world
    }

    private void checkEdgeOrWall() {
        if(isAtEdge()) {
            getWorld().removeObject(this);
        }
        if((Wall)getOneIntersectingObject(Wall.class) != null){
            getWorld().removeObject(this);
        }
    }
}

