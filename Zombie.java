import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Zombie subclass that extends from Enemy.
 * 
 * @author Osmond Lin
 * @author Tony Lin
 * 
 * @version 2024-06-12
 */
public class Zombie extends Enemy
{
    private GreenfootSound zombieDeathSound = new GreenfootSound("zombieSound.mp3"); 
    private int frame = 0, acts = -1;//-1 since before anything begins, the act is incremented by 1 before anything else happens

    /**
     * Constructor for zombie that sets its initial stats and animation
     */
    public Zombie()
    {
        collisionBox = new CollisionBox(30, 20, Box.SHOW_BOXES, this, 0, 20);
        hpBar = new SuperStatBar(hp, hp, this, 50, 8, -33, Color.RED, Color.BLACK, false, Color.YELLOW, 1);
        spd = 2;
        death = false;
        dealtDamage = false;
        inAttack = false;
        //Using Mr Cohen's animation class: 
        down = Animation.createAnimation(new GreenfootImage("Zombie.png"), 10, 1, 9, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("Zombie.png"), 11, 1, 9, 64, 64, 10);
        up = Animation.createAnimation(new GreenfootImage("Zombie.png"), 8, 1, 9, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("Zombie.png"), 9, 1, 9, 64, 64);

        dying = Animation.createAnimation(new GreenfootImage("Zombie.png"), 20, 1, 6, 64, 64);
        setImage(right.getFrame(0));
    }

    public void act()
    {
        acts++;
        if(this.getHp() <= 0)
        {
            inAttack = false;
            zombieDeathSound.play();
        }
        if(this.getHp() > 0 && !inAttack)
        {
            findTarget();
            manageAnimation();
            attack();
        }
        if(inAttack) // If attacking:
        {
            attackAnimation();
        }
        super.act();

    }

    /**
     * Method that allows zombies to find a player to target
     */
    public void findTarget(){
        Player player = (Player)getClosestInRange(Player.class, 500);
        if(player != null){
            pathToEntity(player);
        }
    }

    /**
     * Method that manages the zombie's attack
     */
    public void attack()
    {
        List<Player> target = getObjectsInRange(30, Player.class);
        if(!target.isEmpty())
        {
            inAttack = true;
            down = Animation.createAnimation(new GreenfootImage("Zombie.png"), 6, 1, 8, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("Zombie.png"), 7, 1, 8, 64, 64, 10);
            up = Animation.createAnimation(new GreenfootImage("Zombie.png"), 4, 1, 8, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("Zombie.png"), 5, 1, 7, 64, 64);
            frame = 0;
            target.get(0).damage(5);
        }

    }

    /**
     * Method that manages animation.
     */
    public void manageAnimation()
    {
        if(acts%(5) == 0)
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
    /**
     * Method that manages attack animation.
     */
    public void attackAnimation()
    {
        if(frame == up.getAnimationLength()-1)
        {
            frame = 0;
            inAttack = false;
            down = Animation.createAnimation(new GreenfootImage("Zombie.png"), 10, 1, 9, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("Zombie.png"), 11, 1, 9, 64, 64, 10);
            up = Animation.createAnimation(new GreenfootImage("Zombie.png"), 8, 1, 9, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("Zombie.png"), 9, 1, 9, 64, 64);
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
        if(acts % 3 == 0)
        {
            frame = (frame+1)%(up.getAnimationLength());
        }
    }
    /**
     * Method that manages the death animation
     */
    public void deathAnimation(int timeInBetween, int length)
    {
        if(!death)
        {
            frame = 0;
            spd = 0;
            death = true;
        }
        if(acts % timeInBetween == 0)
        {
            frame = (frame+1)%(length);
        }
        setImage(dying.getFrame(frame));
        if(frame == (length-1))
        {
            die();
        }
    }
}
