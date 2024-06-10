import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Desructible here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Barrel extends Destroyable
{
    /**
     * Act - do whatever the Desructible wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public static GreenfootImage img = new GreenfootImage("barrel.png");
    public Barrel() {
        super(img, 10);
    }
    public void onDestroy() {
        int randomDrop = Greenfoot.getRandomNumber(7);
        if (randomDrop == 0 ||randomDrop == 1) {
            getWorld().addObject(new Coin(), getX(), getY());
        }
        else if (randomDrop == 2){
            getWorld().addObject(new PowerUp(), getX(), getY());
        }
        else if(randomDrop == 3){
            getWorld().addObject(new Heal(Utility.randomIntInRange(30, 60)), getX(), getY());
        }
        replaceMe(new EmptyFloor());
        
    }
    public String getID() {
        return "b";
    }
}
