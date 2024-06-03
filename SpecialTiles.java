import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SpecialTiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class SpecialTiles extends Floor
{
    protected HiddenBox hiddenBox; 
    public SpecialTiles(GreenfootImage img){
        super(img);
        initBox();
    }
    public SpecialTiles(Animation anim){
        super(anim);
        initBox();
    }
    public void addedToWorld(World w) {
        w.addObject(hiddenBox, getX(), getY());
    }
    public void initBox(){
        hiddenBox = new HiddenBox(Cell.SIZE, Cell.SIZE, Box.SHOW_BOXES, this);
    }
    public void act()
    {
        // Add your action code here.
    }
}
