import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Player's Sword Ultimate ability: Crescent strikes that are ranged 
 * and can block projectiles, namely Arrows.
 * 
 * @Tony Lin
 * @version (a version number or a date)
 * Image Credits: https://www.google.com/search?q=sword+slash+png&client=opera-gx&hs=15o&sca_esv=b1c93a90692d9bce&udm=2&biw=1233&bih=785&sxsrf=ADLYWIJ1K9vrP-dKEoyDZw0dlo18m8sizQ%3A1718206225186&ei=Eb9pZqLzCtrI0PEPl5Kx-As&ved=0ahUKEwjiv8_9sNaGAxVaJDQIHRdJDL8Q4dUDCBA&uact=5&oq=sword+slash+png&gs_lp=Egxnd3Mtd2l6LXNlcnAiD3N3b3JkIHNsYXNoIHBuZzIFEAAYgAQyBRAAGIAEMgYQABgHGB4yBhAAGAcYHjIIEAAYBRgHGB4yBhAAGAUYHjIGEAAYBRgeSIgUUNIOWIQScAR4AJABAZgB8gGgAYoEqgEFNC4wLjG4AQPIAQD4AQGYAgOgAuYBmAMAiAYBkgcBM6AHkRs&sclient=gws-wiz-serp#vhid=Qh2ToWbfex3NfM&vssid=mosaic
 */
public class Slash extends Projectile
{
    //Instance Variables: 
    private GreenfootImage image = new GreenfootImage("slashing.png");
    private int firstX, firstY;
    private boolean movingHorizontal, initPosition = false;
    /**
     * Act - do whatever the Slash wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Slash(int spd, int dmg, Actor owner, boolean isHorizontal)
    {
        super(spd, dmg, owner);
        setImage(image);
        movingHorizontal = isHorizontal;
        image.scale(image.getWidth()/4, image.getHeight()/4);
    }

    public void act()
    {
        //Initialize Spawn positions, image orientation
        if(!initPosition)
        {
            if(movingHorizontal)
            {
                if(speed > 0)
                {
                    setImage(image);
                }
                else
                {
                    image.mirrorHorizontally();
                }
            }
            else
            {
                if(speed > 0)
                {
                    setRotation(90);
                }
                else
                {
                    setRotation(270);
                }
            }
            firstX = getX();
            firstY = getY();
            initPosition = true;
        }
        if(getWorld() != null)
        {
            if(movingHorizontal)
            {
                setLocation(getX()+speed, getY());
            }
            else
            {
                setLocation(getX(), getY()+speed);
            }
            doDamage();
        }
    }
    @Override
    /**
     * Method responsible for this ranged slash to deal multi-target damage up to a set amount of distance ahead of the 
     * striking direction from the player.
     */

    public void doDamage() {
        List<Damageable> a = (List<Damageable>)getIntersectingObjects(Damageable.class);
        if(isTouching(Arrow.class)){
            removeTouching(Arrow.class);
        }
        double changeInPosition = Math.sqrt(Math.pow(this.getX() - firstX, 2) + Math.pow(this.getY() - firstY, 2));
        if(changeInPosition < 150)
        {
            if (a.size() == 0) {
                return;
            }
            for(Damageable d : a)
            {
                if(d == owner || d == null)
                {
                    return;
                }
                else
                {
                    d.damage(damage);
                }
            }
            hasHit = true; // Set the flag to true once damage is dealt
        }
        else
        {
            getWorld().removeObject(this); // Remove Projectile.
        }

    }
}
