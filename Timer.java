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
    private long save;
    private boolean running;
    /**
     * Constructor for objects of class Timer
     */
    public Timer()
    {
        mark();
    }
    public Timer(boolean startRunning) {
        if (running) {
            mark();
        } else {
            fullStop();
        }
    }
    public void reset() {
        mark = count;
        running = true;
    }
    public void mark() {
        mark = count;
        running = true;
    }
    public void stop() {
        save = actsPassed();
        running = false;
    }
    public void fullStop() {
        save = 0;
        running = false;
    }
    public long actsPassed() {
        if (running) return count - mark;
        else return save;
    }
    public long acts() {
        if (running) return count - mark;
        else return save;
    }
    public static void tick() {
        count++;
    }
}