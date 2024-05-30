import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FadeEffect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FadeEffect extends CellEffect
{
    /**
     * Act - do whatever the FadeEffect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private Color c;
    private int initialTransparency;
    public FadeEffect(Color c, int initialTransparency) {
        this.initialTransparency = initialTransparency;
        this.c = c;
        img = new GreenfootImage(Cell.SIZE, Cell.SIZE);
        img.setColor(c);
        img.fill();
        img.setTransparency(initialTransparency);
        setImage(img);
    }
    public void act()
    {
        img.setTransparency(Math.max(0, img.getTransparency()-1));
        if (img.getTransparency() <= 0) {
            cell.removeEffect(this);
        }
    }
    public void applyEffect() {
        return;
    }
    public void removeEffect() {
        return;
    }
    public FadeEffect clone() {
        return new FadeEffect(c, initialTransparency);
    }
}
