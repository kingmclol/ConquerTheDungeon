import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Slash here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Slash extends Projectile
{
    private GreenfootImage image = new GreenfootImage("slashing.png");
    //https://www.google.com/search?q=vector+sword+slash+png&udm=2&client=opera-gx&sa=X&ved=2ahUKEwjzs_7--tGGAxUG5MkDHdddCDAQrNwCegQIbhAA&biw=1233&bih=785&dpr=2#imgrc=SO3HXq7GoXOBBM&imgdii=57YLbjVYe0OjwM
    private int x,y;
    /**
     * Act - do whatever the Slash wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Slash(int spd, int dmg, Actor owner)
    {
        super(spd, dmg, owner);
        setImage(image);
        image.scale(image.getWidth()/4, image.getHeight()/4);
        x = getX();
        y = getY();
    }
    public void act()
    {
        // Add your action code here.
        if(getWorld() != null)
        {
            doDamage();
        }
        die();
    }
    public void die()
    {
        int changeInPosition = Math.abs(this.getX()-x) + Math.abs(this.getY()-y);
        if(changeInPosition >= 100)
        {
            getWorld().removeObject(this);
        }
    }
}
