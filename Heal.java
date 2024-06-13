import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Heal class represents a healing item that the player can collect to restore health.
 */
public class Heal extends Drop
{
    private GreenfootSound powerUpSound = new GreenfootSound("powerUpSound.mp3");
    
    private int healAmount, actsBeforeExpired = 3600, frame = 0, acts = 0;
    private boolean spawning = true;
    private Animation spawn, idle, expiry;

    public Heal(int healAmount) {
        this.healAmount = healAmount;
        //add healing spritesheet here
        idle = Animation.createAnimation(new GreenfootImage("Heal/idle.png"), 0, 1, 8, 125, 85, 5, 20, 255, 50);
        spawn = Animation.createAnimation(new GreenfootImage("Heal/spawn.png"), 0, 1, 8, 125, 85, 5, 20, 255, 50);
    }

    public void act() {
        checkCollisionWithPlayer();
    }

    public void checkCollisionWithPlayer() {
        acts++;
        if(!spawning)
        {
            Player player = (Player) getOneIntersectingObject(Player.class);
            animate();
            if (player != null) {
                powerUpSound.play();
                player.heal(healAmount);
                getWorld().addObject(new HealingEffect(player, -20, -10), player.getX(), player.getY()); // up Left
                getWorld().addObject(new HealingEffect(player, -15, 30), player.getX(), player.getY()); // down left
                getWorld().addObject(new HealingEffect(player, 35, 0), player.getX(), player.getY()); // middle right
                getWorld().removeObject(this);
            }
        }
        else
        {
            spawnIn();
        }
    }
    public void animate()
    {
        if(acts % 10 == 0)
        {
            frame = (frame+1) % (idle.getAnimationLength());
        }
        setImage(idle.getFrame(frame));
    }

    public void spawnIn()
    {
        if(acts % 10 == 0)
        {
            frame = (frame+1) % (spawn.getAnimationLength());
        }
        setImage(spawn.getFrame(frame));
        if(frame == (spawn.getAnimationLength()-1))spawning = false;
    }
}

