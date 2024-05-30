import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Enemy extends Entity {
    private int hp, mvtSpd = 2;
    private String defaultFacing = "right";
    public Enemy() {
        super(Team.ENEMY, 100);
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
    public void chasePlayer()
    {
        if(Player.returnX() < this.getX() && Player.returnY() > this.getY())
        {
            defaultFacing = "left";
            setLocation(this.getX()-mvtSpd, this.getY()+mvtSpd);
        }
        else if(Player.returnX() < this.getX() && Player.returnY() < this.getY())
        {
            defaultFacing = "left";
            setLocation(this.getX()-mvtSpd, this.getY()-mvtSpd);
        }
        else if(Player.returnX() > this.getX() && Player.returnY() > this.getY())
        {
            defaultFacing = "right";
            setLocation(this.getX()+mvtSpd, this.getY()+mvtSpd);
        }
        else if(Player.returnX() > this.getX() && Player.returnY() < this.getY())
        {
            defaultFacing = "right";
            setLocation(this.getX()+mvtSpd, this.getY()-mvtSpd);
        }
        else if(Player.returnX() < this.getX())
        {
            defaultFacing = "left";
            setLocation(this.getX()-mvtSpd, this.getY());
        }
        else if(Player.returnX() > this.getX())
        {
            defaultFacing = "right";
            setLocation(this.getX()+mvtSpd, this.getY());
        }
        else if(Player.returnY() > this.getY())
        {
            setLocation(this.getX(), this.getY()+mvtSpd);
        }
        else if(Player.returnY() < this.getY())
        {
            setLocation(this.getX(), this.getY()-mvtSpd);
        }
    }
}

