import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Enemy extends Entity {
    private int hp, mvtSpd = 2, x, y, rotation;//X and Y are Location
    protected boolean inAttack, death, dealtDamage, recievedDamage = false;
    private String facing = "right";
    public Enemy() {
        super(Team.ENEMY, 100);
        this.hp = 50;  
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
        //pathToEntity(null); // Come on i need a player reference somewhere...
        //manageCollision();
    }

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public void die() {
        getWorld().removeObject(this);
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
    public boolean damaged()
    {
        return recievedDamage;
    }
    public void setDamagedState(boolean x)
    {
        recievedDamage = x;
    }
}

