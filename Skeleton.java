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

    public void attack() {
        arrowCooldown++;
        if (arrowCooldown >= 60 && player != null) {
            // Check if there's a wall between the skeleton and the player
            Vector skeletonPos = new Vector(getX(), getY());
            Vector playerPos = new Vector(player.getX(), player.getY());

            // Create a segment from the skeleton to the player
            Segment lineOfSight = new Segment(skeletonPos, playerPos);
            getWorld().addObject(lineOfSight, getX(), getY()); // Add the segment to the world
            lineOfSight.draw();
            // Check for intersection with walls
            System.out.println(lineOfSight.intersectsWall());
            if (!lineOfSight.intersectsWall()) {
                // If no collision with walls, shoot the arrow
                Arrow arrow = new Arrow(4, 10, this, player);
                getWorld().addObject(arrow, getX(), getY());
                arrowCooldown = 0;
            }
            getWorld().removeObject(lineOfSight);
        }
    }

    public void manageAnimation()
    {

    }

    public void deathAnimation()
    {
        die();

    }

    public void attackAnimation()
    {

    }
}
