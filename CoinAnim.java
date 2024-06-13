import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Simple animated image for coins used for the Stats UI.
 */
public class CoinAnim extends UI
{
    private GreenfootImage[] animate = new GreenfootImage[6];
    private int frame = 0, acts = 0;
    public CoinAnim(){
        for(int i = 0; i<animate.length; i++)
        {
            //animate[i] = new GreenfootImage("Star/star" + (i+1) + ".png");
            animate[i] = new GreenfootImage("Coin/coin" + (i + 1) + ".png");
            animate[i].scale(25, 25);
        }
        setImage(animate[0]);
    }


    public void act()
    {
        
        animate();
        acts++;
    }
    public void animate()
    {   
        
        if(acts%7 == 0)
        {
            frame = (frame+1) % (animate.length);
        }
        setImage(animate[frame]);
    }
}
