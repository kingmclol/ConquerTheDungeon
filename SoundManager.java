import greenfoot.*;
import java.util.*;
/**
 * <p>Manages sounds, I guess.</p>
 * 
 * @author Unknown
 * @version Uknown
 */
public class SoundManager  
{
    private static HashMap<String,Queue<GreenfootSound>> sounds = new HashMap<String,Queue<GreenfootSound>>();
    /**
     * Seems like it adds a sound to the soundmanager.
     * @param numberOfSounds the number of sounds objects to create.
     * @param name The filename of the sound
     * @param vol Volume to be
     */
    public static void addSound(int numberOfSounds, String name, int vol) {
        sounds.put(name, fillQueue(numberOfSounds, name, vol));
    }
    /**
     * Plays a sound of the given filename
     */
    public static void playSound(String name) {
        sounds.get(name).peek().play();
        sounds.get(name).add(sounds.get(name).poll());
    }
    /**
     * Fills in a queue with GreenfootSound objects?
     * @param n Number of objects to fill with
     * @param name The filename of the greenfootsound
     * @param vol The volume of the sounds
     */
    public static Queue<GreenfootSound> fillQueue(int n, String name, int vol) {
        Queue<GreenfootSound> temp = new LinkedList<GreenfootSound>();
        for(int i = 0; i<n; i++){
            GreenfootSound gfs = new GreenfootSound(name);
            gfs.setVolume(vol);
            temp.add(gfs);
        }
        return temp;
    }
}
