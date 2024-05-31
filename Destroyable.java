import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Destroyable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Destroyable extends Tile implements Damageable
{
    /**
     * Act - do whatever the Destroyable wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int hp;
    public Destroyable(GreenfootImage img, int hp) {
        super(false, img);
        this.hp = hp;
    }
    public Destroyable(Color c, int hp){
        super(false, c);
        this.hp =hp;
    }
    /**
     * Temp.
     */
    public Destroyable(Color c) {
        super (false, c);
        this.hp = -1;
    }
    public int damage(int damage) {
        hp-=damage;
        if (hp <= 0) onDestroy();
        return 0;
    }
    protected abstract void onDestroy();
}
