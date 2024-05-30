import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class PowerUp extends SuperSmoothMover
{
    public PowerUp(){
        //spritesheet
    }
    public void act() {
        checkCollisionWithPlayer();
    }

    private void checkCollisionWithPlayer() {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null) {
            player.activatePowerUp();
            getWorld().removeObject(this);
        }
    }
}
