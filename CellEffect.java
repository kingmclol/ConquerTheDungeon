import greenfoot.*;
/**
 * <p>A CellEffect is something that is applied onto a singular Cell TEMPORARILY, and does something.</p>
 * 
 * </p>For example, "Fire" is an example of a CellEffect-- it changes the appearance of the cell into something firey
 * and has an effect on the Cell itself-- any Entities within would take damage! We never added this though...</p>
 * 
 * 
 * <p>CellEffects should set their own image. In their act() methods, they should do things like their own animations.</p>
 * 
 * <p>This is basically unused at this point after a change of plans...</p>
 * 
 * @author Freeman Wang
 * @version 2024-06-12
 */
public abstract class CellEffect extends Actor
{
    protected Cell cell; // The cell that this CellEffect is active in.
    protected GreenfootImage img;
    public CellEffect()
    {
        
    }
    /**
     * Sets the cell this effect is active on to the given cell
     * @param c The cell to be on.
     */
    public void setCell(Cell c) {
        this.cell = c;
    }
}