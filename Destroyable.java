import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Destroyable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Destroyable extends Tile
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
    public void damage(int damage) {
        hp-=damage;
        if (hp <= 0) onDestroy();
    }
    protected abstract void onDestroy();
}
