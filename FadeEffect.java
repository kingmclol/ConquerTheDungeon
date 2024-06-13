import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Creates a effect on a cell that is of a colour and fades out over time.
 * 
 * @author Freeman Wang
 * @version May 2024
 */
public class FadeEffect extends CellEffect
{
    private Color c;
    private int initialTransparency;
    /**
     * Creates a FadeEffect of the given colour and initial transparency.
     * @param c color to use
     * @param initialTransparency the initial transpoarnency of the effect
     */
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
