import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * The Green looking Goblin in the game.
 * 
 * @Tony Lin
 * @version (a version number or a date)
 * Art Credits: https://opengameart.org/content/lpc-goblin
 */
public class Goblin extends Enemy
{
    private int frame = 0, acts = -1;//-1 since before anything begins, the act is incremented by 1 before anything else happens
    private List<Player> target;
    /**
     * Act - do whatever the Goblin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Goblin()
    {
        collisionBox = new CollisionBox(30, 20, Box.SHOW_BOXES, this, 0, 20);
        hpBar = new SuperStatBar(hp, hp, this, 50, 8, -33, Color.RED, Color.BLACK, false, Color.YELLOW, 1);
        spd = 2;
        death = false;
        dealtDamage = false;
        inAttack = false;
        //Using Mr Cohen's animation class: 
        down = Animation.createAnimation(new GreenfootImage("goblin.png"), 0, 1, 7, 64, 64);
        right = Animation.createAnimation(new GreenfootImage("goblin.png"), 1, 1, 7, 64, 64);
        up = Animation.createAnimation(new GreenfootImage("goblin.png"), 2, 1, 7, 64, 64);
        left = Animation.createAnimation(new GreenfootImage("goblin.png"), 3, 1, 7, 64, 64);

        dying = Animation.createAnimation(new GreenfootImage("goblinsword.png"), 4, 1, 5, 64, 64);
        setImage(right.getFrame(0));
    }

    public void act()
    {
        target = getObjectsInRange(30, Player.class);
        // Add your action code here.
        acts++;
        if(this.getHp() <= 0)
        {
            inAttack = false;
        }
        if(this.getHp() > 0 && !inAttack)
        {
            drawWeapon();
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
            deathAnimation();
        }
        if(!death)
        {
           super.act(); 
        }
    }

    public void findTarget(){
        Player player = (Player)getClosestInRange(Player.class, 500);
        if(player != null){
            pathToEntity(player);
        }
    }

    public void attack()
    {

        if(!target.isEmpty())
        {
            inAttack = true;
            down = Animation.createAnimation(new GreenfootImage("attack.png"), 0, 1, 4, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("attack.png"), 1, 1, 4, 64, 64, 10);
            up = Animation.createAnimation(new GreenfootImage("attack.png"), 2, 1, 4, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("attack.png"), 3, 1, 4, 64, 64);
            frame = 0;
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
            down = Animation.createAnimation(new GreenfootImage("goblin.png"), 0, 1, 7, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("goblin.png"), 1, 1, 7, 64, 64);
            up = Animation.createAnimation(new GreenfootImage("goblin.png"), 2, 1, 7, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("goblin.png"), 3, 1, 7, 64, 64);
            List<Player> players = getWorld().getObjects(Player.class);
            for(Player p : players)
            {
                p.setDamagedState(false);
            }
        }
        if(frame == 2 && !target.isEmpty())
        {
            if(target.get(0).damaged() == false)
            {
                target.get(0).damage(5);
                target.get(0).setDamagedState(true);
            }
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

    /**
     * Near the Player, draw weapon.
     */
    public void drawWeapon()
    {
        List<Player> target = getObjectsInRange(100,Player.class);
        //If Player is Nearby in range:
        if(!target.isEmpty())
        {
            down = Animation.createAnimation(new GreenfootImage("goblinsword.png"), 0, 1, 7, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("goblinsword.png"), 1, 1, 7, 64, 64);
            up = Animation.createAnimation(new GreenfootImage("goblinsword.png"), 2, 1, 7, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("goblinsword.png"), 3, 1, 7, 64, 64);
            spd = 2;
        }
        else
        {
            down = Animation.createAnimation(new GreenfootImage("goblin.png"), 0, 1, 7, 64, 64);
            right = Animation.createAnimation(new GreenfootImage("goblin.png"), 1, 1, 7, 64, 64);
            up = Animation.createAnimation(new GreenfootImage("goblin.png"), 2, 1, 7, 64, 64);
            left = Animation.createAnimation(new GreenfootImage("goblin.png"), 3, 1, 7, 64, 64);
            spd = 3;
        }
    }
}
