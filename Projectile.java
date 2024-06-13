import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.List;

/**
 * Manages the traits off all projectiles
 * 
 * <p> Projectiles can do damage to all entities except the entity that shot the
 * projectile, meaning enemies can damage each other.
 * 
 * <p> A projectile can affect all damageables, which includes entities and destroyable
 * objects.
 * 
 * <p> If a projectile hits a wall, it will disappear.
 * 
 * @author Osmond Lin
 * @version 2024-06-12
 */

public abstract class Projectile extends SuperSmoothMover {
    private int spawnOffset = 10;
    private int rotation;
    protected int speed;
    protected int damage;
    protected Actor owner;
    
    protected boolean hasHit = false; // Flag to track if the projectile has already hit something

    /**
     * Constructor that all projectiles will inherit and add upon
     * 
     * @param spd    The speed of the projectile
     * @param dmg    The amount of damage done by the projectile
     * @param own    The owner of this projectile, or the entity who shot it
     */
    public Projectile(int spd, int dmg, Actor own) {
        speed = spd;
        damage = dmg;
        owner = own;
    }
    
    public void act() {
        move(speed); 
        if (getWorld() != null) {
            doDamage();
        }
        if(getWorld() != null) {
            checkEdgeOrWall(); // Removes projectile if it hits a wall or reaches the end of the world   
        }
    }

    /**
     * Method where the projectile will do damage to all damageable actors
     */
    public void doDamage() {
        //Finds a damageable that the projectile hit
        Damageable a = (Damageable)getOneIntersectingObject(Damageable.class);

        if (a == null || a == owner) { //If projectile hits owner, remove and do nothing
            return;
        }

        a.damage(damage); 
        getWorld().removeObject(this); // Remove the projectile from the world
    }

    /**
     * Method that removes projectile if it hits a wall or leaves the world's dimensions
     */
    private void checkEdgeOrWall() {
        if(isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }
        if((Wall)getOneIntersectingObject(Wall.class) != null){
            getWorld().removeObject(this);
        }
    }
}

