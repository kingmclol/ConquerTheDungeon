import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

public class Zombie extends Enemy
{
    private GreenfootSound zombieDeathSound = new GreenfootSound("zombieSound.mp3"); 
    private int frame = 0, acts = -1;//-1 since before anything begins, the act is incremented by 1 before anything else happens

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

        dying = Animation.createAnimation(new GreenfootImage("goblinsword.png"), 4, 1, 5, 64, 64);
        setImage(right.getFrame(0));
    }

    public void act()
    {
        // Add your action code here.
        acts++;
        if(this.getHp() <= 0)
        {
            inAttack = false;
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
        if(this.getHp() <= 0) // trigger death animation.
        {
            zombieDeathSound.play();
            deathAnimation();
        }
        super.act();
    }

    public void findTarget(){
        Player player = (Player)getClosestInRange(Player.class, 500);
        if(player != null){
            pathToEntity(player);
        }
    }

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
     * manages Animation.
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

    public void deathAnimation()
    {
        if(!death)
        {
            frame = 0;
            spd = 0;
            death = true;
        }
        if(acts % 10 == 0)
        {
            frame = (frame+1)%(dying.getAnimationLength());
        }
        setImage(dying.getFrame(frame));
        if(frame == (dying.getAnimationLength()-1))
        {
            die();
        }
    }

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
}
