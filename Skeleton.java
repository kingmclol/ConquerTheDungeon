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
        setImage(right.getFrame(0));
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
                inAttack = true;
                manageRotation();
                down = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 6, 1, 8, 64, 64);
                right = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 7, 1, 8, 64, 64, 10);
                up = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 4, 1, 8, 64, 64);
                left = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 5, 1, 8, 64, 64);
                Arrow arrow = new Arrow(4, 10, this, player);
                getWorld().addObject(arrow, getX(), getY());
                arrowCooldown = 0;
                attackAnimation();
            }
            getWorld().removeObject(lineOfSight);
        }
    }

    public void manageAnimation()
    {
        if(acts % 10 == 0)
        {
            frame = (frame + 1) % (up.getAnimationLength());
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
        if(frame == up.getAnimationLength()-1)
        {
            frame = 0;
            inAttack = false;
            up = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 8, 1, 9, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 9, 1, 9, 64, 64);
            down = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 10, 1, 9, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 11, 1, 9, 64, 64, 10);
        }
        switch(this.getFacing())
        {
            case "up":
                setImage(up.getFrame(frame));
                break;
            case "down":
                setImage(down.getFrame(frame));
                break;
            case "right":
                setImage(right.getFrame(frame));
                break;
            case "left":
                setImage(left.getFrame(frame));
                break;
        }
        if(acts % 15 == 0)
        {
            frame = (frame+1)%(up.getAnimationLength());
        }
    }
}
