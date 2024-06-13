import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A barrel is a destroyable object that has randomized drops, including healing, coins, and powerups.
 * 
 * @author Neelan Thurairajah 
 * @version June 2024
 */
public class Barrel extends Destroyable
{

    private GreenfootSound barrelBreakSound = new GreenfootSound("barrelSound.mp3"); 
    public static GreenfootImage img = new GreenfootImage("barrel.png");
    public Barrel() {
        super(img, 10);
        barrelBreakSound.setVolume(50);
    }
    public void onDestroy() {
        barrelBreakSound.play(); 
        // Generate a random number
        int randomDrop = Greenfoot.getRandomNumber(11);
        // Drops with coins weighted more
        if (randomDrop == 0 ||randomDrop == 1 ||randomDrop == 4) {
            getWorld().addObject(new Coin(), getX(), getY());
        }
        else if (randomDrop == 2){
            getWorld().addObject(new PowerUp(), getX(), getY());
        }
        else if(randomDrop == 3){
            getWorld().addObject(new Heal(Utility.randomIntInRange(10, 25)), getX(), getY());
        }
        //replace barrel with floor once destroyed
        replaceMe(new EmptyFloor());
        
    }
    public String getID() {
        return "b";
    }
}
