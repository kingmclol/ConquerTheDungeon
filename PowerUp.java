import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class PowerUp extends Drop
{
    private GreenfootImage[] animate = new GreenfootImage[3];
    private int frame = 0, acts = 0;
    
    public PowerUp(){
        //spritesheet
        for(int i = 0; i<animate.length; i++)
        {
            animate[i] = new GreenfootImage("Star/star" + (i+1) + ".png");
            animate[i].scale(this.getImage().getHeight()*2, this.getImage().getWidth()*250/100);
        }
        
    }
    public void act() {
        checkCollisionWithPlayer();
        animate();
        acts++;
        
    }

    public void checkCollisionWithPlayer() {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if (player != null) {
            player.activatePowerUp();
            getWorld().removeObject(this);
        }
    }
    public void animate()
    {
        if(acts%10 == 0)
        {
            frame = (frame+1) % (animate.length);
        }
        setImage(animate[frame]);
    }
}
