/**
 * Simple Timer that counts the number of acts that has elapsed since its creation, based on the acts that
 * The World executes.
 * 
 * In the World's Act method, include Timer.tick() for it to work.
 */
public class Timer
{
    // instance variables - replace the example below with your own
    private static long count = 0;
    private long mark;
    /**
     * Constructor for objects of class Timer
     */
    public Timer()
    {
        mark = count;
    }
    public void reset() {
        mark = count;
    }
    public long actsPassed() {
        return count - mark;
    }
    public long acts() {
        return count - mark;
    }
    public static void tick() {
        count++;
    }
}