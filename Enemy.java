import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Subclass of Entity, and superclass of all enemies that manages their traits and behaviours
 * 
 * <p> Enemies will target the player when player comes within a certain range.
 * 
 * <p> Enemies can damage each other if they are close enough to each other.
 * 
 * @author Osmond Lin
 * @author Tony Lin
 * 
 * @version 2024-06-12
 */
public abstract class Enemy extends Entity {
    //Animations for Moving as well as other object behaviour: 
    protected Animation up,down,left,right, dying;
    
    protected int mvtSpd = 2, x, y, rotation;//X and Y are Location
    
    //Speed/Movement:
    protected double spd;
    private String facing = "right";
    protected Player player;
    
    private static HashMap<String, Class> enemyDatabase;
    private static ArrayList<String> enemyIDs;
    
    /**
     * Constructor of Enemy that calls the entity superclass
     */
    public Enemy() {
        super(100);
        this.hp = 100;  
    }
    
    public void act() {
        super.act();
        player = (Player)getClosestInRange(Player.class, 1000);
        manageCollision();
    }
    
    /**
     * Method that allows enemies to path towards player
     */
    public void pathToEntity(Entity e) {
        // weird way to get player position, but ok i guess i just want to test 
        pathTowards(e, mvtSpd);
        manageRotation();
    }
    
    public void addedToWorld(World w) {
        super.addedToWorld(w);
    }
    
    /**
     * Abstract method that allows all enemies to have the attack method
     */
    protected abstract void attack();

    /**
     * Abstract method that allows all enemies to have the ability to track a target
     */
    protected abstract void findTarget();
    /**
     * Method that removes enemy when it dies and in place of it spawns a coin
     */
    public void die(){
        getWorld().addObject(new Coin(), getX(), getY());
        getWorld().removeObject(this);
    }
    
    /**
     * Method that damages enemies
     * 
     * @param damage  The amount of damage to be taken
     */
    public void takeDamage(int damage)
    {
        hp -= damage;
    }
    
    /**
     * Method that manages the rotation of enemies
     */
    public void manageRotation()
    {
        int rotation = getRotation();
        if((rotation >= 0 && rotation <= 45) || (rotation > 315 && rotation < 360))
        {
            facing = "right";
        }
        else if(rotation > 45 && rotation <= 135)//between 45-135 && between 135 and 225
        {
            facing = "down";
        }
        else if(rotation > 135 && rotation <= 225)//135 and 180, 180 to 225
        {
            facing = "left";
        }
        else if((rotation > 225 && rotation <= 315) || (rotation >= 135 || rotation <= -45))
        {
            facing = "up";
        }
    }

    /**
     * Method that returns the direction that the enemy is facing
     * 
     * @return facing  The direction that the enemy is facing
     */
    public String getFacing()
    {
        return facing;
    }

    /**
     * Method that returns the enemy's health
     * 
     * @return hp   The enemy's current hp
     */
    public int getHp()
    {
        return hp;
    }
    
    /**
     * Method that returns if the enemy is attacking
     * 
     * @return inAttack   boolean that determines if the enemy is current attacking
     */
    public boolean whetherAttacking()
    {
        return inAttack;
    }
    
    /**
     * Method that returns if the enemy is alive or not
     * 
     * @return death   boolean that determines if the enemy is dead
     */
    public boolean getDeath()
    {
        return death;
    }
    /**
     * Gets an instance of an enemy given its ID.
     * @return the enemy instance as an entity
     */
    public static Entity getInstanceFromID(String id) {
        Class c = enemyDatabase.get(id);
        if (c == null) {
            if (GameWorld.SHOW_LOGS) System.out.println("err: the enemy ID \"" + id + "\" was invalid");
            return new Goblin(); // fallback to goblin
        }
        
        try
        {
            return (Entity) c.newInstance();
        }
        catch (IllegalAccessException iae) {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: encountered IllegalAccessException from enemy ID: " + id);
        }
        catch (InstantiationException ie)
        {
            if (GameWorld.SHOW_LOGS) System.out.println("warn: encountered InstantiationException from enemy ID: " + id);
        }
        return new Goblin(); // Something really bad went wrong. ouch. fallback to goblin
    }
    /**
     * Returns a random enemy ID.
     * @return the enemy ID.
     */
    public static String getRandomEnemyID() {
        if (enemyIDs == null && enemyDatabase != null) {
            enemyIDs = new ArrayList<String>(enemyDatabase.keySet());
        }
        return enemyIDs.get(Greenfoot.getRandomNumber(enemyIDs.size()));
    }
    /**
     * Initilalizes the map of ID -> Class pairs for the enemies.
     */
    public static void initializeEnemyDatabase() {
        if (enemyDatabase != null) return;
        enemyDatabase = new HashMap<String, Class>();
        enemyDatabase.put("gob", Goblin.class);
        enemyDatabase.put("ske", Skeleton.class);
        enemyDatabase.put("zom", Zombie.class);
        if (GameWorld.SHOW_LOGS) System.out.println("info: loaded in enemy database");
    }
    public void setFacing(String facing)
    {
        this.facing = facing;
    }
}

