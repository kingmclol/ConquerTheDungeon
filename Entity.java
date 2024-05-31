import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Entities are "mobs" that can do Actions throughout the game. They can be on your team (an ALLY) or against (an ENEMY).
 * 
 * An Entity has to undergo combat through turns (as this is a turn-based game) They also have their individual statusEffects
 * and Decks.
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Entity extends SuperActor implements Damageable
{
    // The enum Team stores whether the Entity is on your same team or not (Ally vs hostile)
    public enum Team {
        ALLY,
        ENEMY
    }
    // public static final int MAX_SKILL_POINTS = 5;
    // protected HashMap<StatusModifier.Trigger, ArrayList<StatusModifier>> statusModifiers; // Stores modifiers of each trigger type.
    // // protected HealthBar healthBar;
    // protected Team team;
    // protected int hp;
    // protected ArrayList<Cell> path;
    public int hp;
    public Entity(Team team, int maxHp) {
        // this.team = team;
        // hp = maxHp;
        hp = maxHp;
        // // healthBar = new HealthBar(maxHp);
        // skillPoints = MAX_SKILL_POINTS/2;
        // //Deck = new Deck(Deck.Preset.A);
        // // Initializing the HashMap.
        // intent = Intent.NONE;
        // path = null;
        // moveTimer = new Timer();
        // statusModifiers = new HashMap<StatusModifier.Trigger, ArrayList<StatusModifier>>();
        // for (StatusModifier.Trigger t : StatusModifier.Trigger.values()) { // For types of status trigger times,
            // statusModifiers.put(t, new ArrayList<StatusModifier>()); // Greate an arraylist to keep track of them.
        // }
    }
    public void act() {
        //animate();
    }
    // /**
     // * Given a StatusModifier m, apply it onto this Entity.
     // */
    // public void applyModifier(StatusModifier m) {
        // // Get the ARrayList that holds the modifier of same trigger type as m , and add it there.
        // statusModifiers.get(m.trigger).add(m); 
        // m.setOwner(this);
    // }
    // /**
     // * Given a StatusModifier m, remove it from this Entity, if it exists.
     // */
    // public void removeModifier(StatusModifier m) {
        // // Get the ArrayList that holds the modifiers of same trigger type as m, then remove from the arraylist.
        // statusModifiers.get(m.trigger).remove(m);
    // }
    public int damage(int dmg) {
        TextBox dmgBox = new TextBox("-" + dmg, 24, Color.ORANGE, null, 2, 255);
        getWorld().addObject(dmgBox, getX()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE), getY()-Cell.SIZE/2+Greenfoot.getRandomNumber(Cell.SIZE));
        dmgBox.fadeOut();
        int dmgTaken;
        if (hp <= dmg) dmgTaken = hp;
        hp -= dmg;
        dmgTaken = dmg;
        if (hp <= 0) die();
        return dmgTaken;
    }
    public void heal(int heal) {
        // TextBox dmgBox = new TextBox("+" + heal, 24, Color.GREEN, null, 2, 255);
        // getWorld().addObject(dmgBox, getX()-Tile.tileSize/2+Greenfoot.getRandomNumber(GameWorld.tileSize), getY()-GameWorld.tileSize/2+Greenfoot.getRandomNumber(GameWorld.tileSize));
        // dmgBox.fadeOut();
        // hp+=heal;
    }
    //protected abstract void animate();
    public void die() {
        return;
    }
    public boolean isAlive() {
        //return hp >= 0;
        return false;
    }
}
