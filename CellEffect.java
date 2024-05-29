import greenfoot.*;
/**
 * <p>A CellEffect is something that is applied onto a singular Cell, and does something.</p>
 * 
 * </p>For example, "Fire" is an example of a CellEffect-- it changes the appearance of the cell into something firey
 * and has an effect on the Cell itself-- any Entities within would take damage!</p>
 * 
 * <p>There is an important distinction between <code>applyEffect()</code> and <code>act()</code>:</p>
 * 
 * <p>CellEffects should set their own image. In their act() methods, they should do things like their own animations. On the other
 * had, applyEffect() should do things related to its effect, such as making the Cell deemed as inaccessible, or applying a debuff
 * onto any Entities within that Cell, and of course, decreasing its lifespan by one turn.</p>
 * 
 * <p> wait this doesn't seem right. I'll have to look over this later.</p>
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class CellEffect extends Actor
{
    protected Cell cell; // The cell that this CellEffect is active in.
    protected GreenfootImage img;
    public CellEffect()
    {
        
    }
    public abstract void applyEffect();
    public abstract void removeEffect();
    public abstract CellEffect clone();
    public void setCell(Cell c) {
        this.cell = c;
    }
}