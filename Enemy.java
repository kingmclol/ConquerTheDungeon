import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;
import java.util.ArrayList;

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
    public Enemy() {
        super(Team.ENEMY, 100);
        this.hp = 100;  
    }
    /**
     * THIS IS TEMPORARY IMPLEMENTATION. I NEED A PLAYER REFERENCE SOMEWHERE
     */
    public void pathToEntity(Entity e) {
        // weird way to get player position, but ok i guess i just want to test 
        pathTowards(e, mvtSpd);
        manageRotation();
    }
    public void addedToWorld(World w) {
        super.addedToWorld(w);
    }
    
    protected abstract void attack();

    /**
     * Act - do whatever the Enemy wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public void act() {
        super.act();
        player = (Player)getClosestInRange(Player.class, 1000);
        manageCollision();
    }
    
    public void takeDamage(int damage)
    {
        hp -= damage;
    }
    public void manageRotation()
    {
        /*x = getX();
        y = getY();
        //Find change in X and Y with respect to Player Position:
        int deltaX = Player.returnX() - x;
        int deltaY = Player.returnY() - y;
        rotation = (int) Math.toDegrees(Math.atan2(deltaX,deltaY));*/
        // Normalize the angle to be within the range [0, 360)
        // if (rotation < 0) {
            // rotation += 360;
        // }
        // rotation = rotation%360 - 90;
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

    public String getFacing()
    {
        return facing;
    }

    public int getHp()
    {
        return hp;
    }
    public boolean whetherAttacking()
    {
        return inAttack;
    }
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

