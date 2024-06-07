import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Skeleton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Skeleton extends Enemy
{
    private int frame = 0, acts = -1;
    private int arrowCooldown;

    public Skeleton(){
        //Animation: 
        up = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 9, 1, 9, 64, 64);
        down = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 11, 1, 9, 64, 64, 10);
        collisionBox = new CollisionBox(30, 20, Box.SHOW_BOXES, this, 0, 20);
        hpBar = new SuperStatBar(hp, hp, this, 50, 8, -33, Color.RED, Color.BLACK, false, Color.YELLOW, 1);
        spd = 2;
        death = false;
        dealtDamage = false;
        inAttack = false;
        arrowCooldown = 60;
    }

    public void act()
    {
        super.act();
        acts++;
        if(player != null && getDistance(player) <= 300){
            inAttack = true;
        }else{
            inAttack = false;
        }
        if(this.getHp() <= 0)
        {
            inAttack = false;
        }
        if(this.getHp() > 0 && !inAttack)
        {
            findTarget();
            manageAnimation();
        }
        if(inAttack) // If attacking:
        {
            attack();
            attackAnimation();
        }
        if(this.getHp() <= 0) // trigger death animation.
        {
            deathAnimation();
        }

    }

    public void findTarget(){

        if(player != null && getDistance(player) >= 300){
            pathToEntity(player);
        }
    }

    public void attack()
    {
        arrowCooldown++;
        if(arrowCooldown >= 60){
            Arrow arrow = new Arrow(4, 10, this, player);
            getWorld().addObject(arrow, getX(), getY());
            arrowCooldown = 0;
        }

    }

    public void manageAnimation()
    {
        if(acts % 5 == 0)
        {
            frame = (frame+1)%(up.getAnimationLength());
        }
        switch(this.getFacing())
        {
            case "right":
                setImage(right.getFrame(frame));
                break;
            case "left":
                setImage(left.getFrame(frame));
                break;
            case "up":
                setImage(up.getFrame(frame));
                break;
            case "down":
                setImage(down.getFrame(frame));
                break;
        }
    }

    public void deathAnimation()
    {
        die();

    }

    public void attackAnimation()
    {

    }
}
