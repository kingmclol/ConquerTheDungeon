import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Heal class represents a healing item that the player can collect to restore health.
 */
public class Heal extends SuperSmoothMover
{
    private int healAmount;

    public Heal(int healAmount) {
        this.healAmount = healAmount;
        //add healing spritesheet here
        
    }

    public void act() {
        checkCollisionWithPlayer();
    }

    private void checkCollisionWithPlayer() {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null) {
            player.heal(healAmount);
            getWorld().removeObject(this);
        }
    }
}

