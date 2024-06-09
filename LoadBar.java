import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LoadBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LoadBar extends UI
{
    private int interpolationTime = 45;
    private double linearRate;
    private double barPercent = 100;
    private double targetPercent = 100;
    private int acts;
    private int width;
    private int height;
    private int depletionTime;
    private boolean interpolation;
    private GreenfootImage img;
    private Color c1;
    private Color c2;
    public LoadBar(int width, int height, Color c1, boolean interpolation){
        this.c1 = c1;
        this.c2 = c1;
        this.width = width;
        this.height = height;
        this.interpolation = interpolation;
        img = new GreenfootImage(width, height);
        img.setColor(c1);
        img.fill();
        
        setImage(img);
    }
    public LoadBar(int width, int height, Color c1, Color c2, boolean interpolation){
        this.width = width;
        this.height = height;
        this.depletionTime = depletionTime;
        this.c1 = c1;
        this.c2 = c2;
        img = new GreenfootImage(width, height);
        img.setColor(c1);
        img.fill();
        
        setImage(img);
    }
    public void act()
    {
  
        if(interpolation){

            double diff = barPercent - targetPercent;

            barPercent -= Math.signum(diff) * Math.max(Math.abs(diff) / interpolationTime, 0.75);

            
            if(Math.signum(diff) == -1 && barPercent >= targetPercent){
                barPercent = targetPercent;
                acts= 0;
            }
            else if(Math.signum(diff) == 1 && barPercent <= targetPercent){
                barPercent = targetPercent;
            }
            if(barPercent < 0.0){
                barPercent = 0;
                
            }
            else if(barPercent > 100.0){
                barPercent = 100;
            }
            
            
            

        }
        else {
            barPercent = targetPercent;
            if(barPercent < 0.0){
                barPercent = 0;
                
            }
            else if(barPercent > 100.0){
                barPercent = 100;
            }
            
        }
        updateBar();
    }
    public void setTargetPercent(double percent){
        targetPercent = percent;
        double diff = barPercent - targetPercent;
        linearRate = diff/ (double) depletionTime;
    }
    public void updateBar() {
        img.clear();
        
        int barWidth = (int) ((double) width * (barPercent/ 100.0));
        img.setColor(c1);
        img.fillRect(0, 0, barWidth, 50);
        if(barPercent == 100.0){
            img.setColor(c2);
            img.fillRect(0, 0, barWidth, 50);
        }
    }
}
