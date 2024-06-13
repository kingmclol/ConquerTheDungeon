import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * LoadBar is an UI element for stats bars and cooldown bars that has options for smooth updating of the bar.
 * 
 * @author Neelan Thurairajah
 * @version (a version number or a date)
 */
public class LoadBar extends UI
{
    private int smoothTime = 30;
    private double linearRate;
    private double barPercent = 100;
    private double targetPercent = 100;
    private int acts;
    private int width;
    private int height;
    private int depletionTime;
    private boolean smooth;
    private GreenfootImage img;
    private Color c1;
    private Color c2;
    private boolean cd = false; 
    private boolean border = false;
    private int opacity;
    
    /**
     * 1-color bordered constructor with width, height, opacity, color, and smoothing options
     * 
     * @param width     Width of the bar
     * @param height    Height of the bar
     * @param c1        Color of the bar
     * @param opacity   Transparency of the bar, only with integers from 0 - 255
     * @param smooth    Controls if the bar should smoothly update when changing value
     */
    public LoadBar(int width, int height, Color c1, int opacity, boolean smooth){
        this.c1 = c1;
        this.c2 = c1;
        this.width = width;
        this.height = height;
        this.smooth = smooth;
        this.border = true;
        this.opacity = opacity;
        img = new GreenfootImage(width, height);
        img.setTransparency(opacity);
        img.setColor(c1);
        img.fill();
    
        setImage(img);
    }
    /**
     * 2-color no border constructor with width, height, opacity, color, and smoothing options
     * 
     * @param width     Width of the bar
     * @param height    Height of the bar
     * @param c1        Color of the bar when not full
     * @param c2        Color of the bar when completely filled
     * @param opacity   Transparency of the bar, only with integers from 0 - 255
     * @param smooth    Controls if the bar should smoothly update when changing value
     */    
    public LoadBar(int width, int height, Color c1, Color c2, int opacity, boolean smooth){
        this.width = width;
        this.height = height;
        this.depletionTime = depletionTime;
        this.c1 = c1;
        this.c2 = c2;
        this.smooth = smooth;
        this.opacity = opacity;
        img = new GreenfootImage(width, height);
        img.setTransparency(opacity);
        img.setColor(c1);
        img.fill();
        
        setImage(img);
    }

    public void act()
    {
  
        if(smooth){
            // difference between target and current
            double diff = barPercent - targetPercent;
            // bar percent changes by a fraction of the difference to smootly transition, speed being based on smoothTime
            barPercent -= Math.signum(diff) * Math.max(Math.abs(diff) / smoothTime, 0.75);
            
            // Check whether the bar is increasing/decreasing and make sure it does not go over target percent of bar
            if(Math.signum(diff) == -1 && barPercent >= targetPercent){
                barPercent = targetPercent;
            }
            else if(Math.signum(diff) == 1 && barPercent <= targetPercent){
                barPercent = targetPercent;
            }
            // Clamp values between 100 % and 0 %
            if(barPercent < 0.0){
                barPercent = 0;
                
            }
            else if(barPercent > 100.0){
                barPercent = 100;
            }
            
            
            

        }
        else {
    
            // Mainly used for cooldowns, where no smoothing is needed as bar is updated every act
            barPercent = targetPercent;
            // Clamp values between 100 % and 0 %
            if(barPercent < 0.0){
                barPercent = 0;
                
            }
            else if(barPercent > 100.0){
                barPercent = 100;
            }
            
        }
        updateBar();
    }
    /**
     * Sets a goal percent that bar will update to.
     */
    public void setTargetPercent(double percent){
        targetPercent = percent;
        
    }
    /**
     * Updates the bar visually
     */
    public void updateBar() {
        img.clear();
        // draw bar
        int barWidth = (int) ((double) width * (barPercent/ 100.0));
        img.setColor(c1);
        img.fillRect(0, 0, barWidth, height);
        // Check when bar is full 
        if(barPercent == 100.0){
            img.setColor(c2);
            // Change color of bar once full
            img.fillRect(0, 0, barWidth, height);
        }
        if(border){
            // Draws border
            img.setColor (new Color(188, 138, 1));
            
            for (int i = 0; i < 2; i++){
                img.drawRect (i, i, width - 1 - (i * 2), height - 1 - (i * 2));
            }  
        }
 
    }
}
