import greenfoot.*;
import java.util.*;
/**
 * Write a description of class SoundManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoundManager  
{
    private static HashMap<String,Queue<GreenfootSound>> sounds = new HashMap<String,Queue<GreenfootSound>>();
    public static void addSound(int numberOfSounds, String name, int vol) {
        sounds.put(name, fillQueue(numberOfSounds, name, vol));
    }
    public static void playSound(String name) {
        sounds.get(name).peek().play();
        sounds.get(name).add(sounds.get(name).poll());
    }
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
