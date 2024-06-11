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
    private boolean hasShot;

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
        hasShot = false;
        setImage(right.getFrame(0));
    }

    public void act()
    {
        acts++;
        if(player != null && player.getWorld() != null && getDistance(player) <= 300){
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
        super.act();
    }

    public void findTarget(){

        if(player != null && player.getWorld() != null && getDistance(player) >= 300){
            pathToEntity(player);
        }
    }

    public void attack() {
        if (!hasShot && frame == 5 && player != null) {
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
                hasShot = true;
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
        setDirection();
        if(frame >= up.getAnimationLength()-1)
        {
            frame = 0;
            inAttack = false;
            up = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 8, 1, 9, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 9, 1, 9, 64, 64);
            down = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 10, 1, 9, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("Skeleton.png"), 11, 1, 9, 64, 64, 10);
            return;
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
        if(frame == 6){
            hasShot = false;
        }
    }
    public void setDirection()
    {
        int x = Player.returnX(), y = Player.returnY();
        //Change in Y and X, vectors.
        x = x - this.getX();
        y = y - this.getY();
        
        double angle = Math.atan2(y, x);
        //Calculate angle:
        
        //Set Facing based off angle:
        if (angle > -Math.PI / 4 && angle <= Math.PI / 4) { // Q4 and Q1, 45 degree cutoff
            setFacing("right");
        } else if (angle > Math.PI / 4 && angle <= 3 * Math.PI / 4) {
            setFacing("down");
        } else if (angle > -3 * Math.PI / 4 && angle <= -Math.PI / 4) {
            setFacing("up");
        } else {
            setFacing("left");
        }
        
    }
}
