/**
 * <p>Simple Timer that counts the number of acts that has elapsed since its creation, based on the acts that
 * the World executes.</p>
 * 
 * <p><strong>In the World's Act method, include <code>Timer.tick()</code> for it to work.</strong></p>
 * 
 * @author freeman Wang
 * @version 2024-06-13
 */
public class Timer
{
    // instance variables - replace the example below with your own
    private static long count = 0;
    private long mark;
    private long save;
    private boolean running;
    /**
     * Create a timer that starts instantly
     */
    public Timer()
    {
        mark();
    }
    /**
     * Creates a timer that may or may not run on creation
     * @param startRunning whether to start the timer instantly
     */
    public Timer(boolean startRunning) {
        if (running) {
            mark();
        } else {
            fullStop();
        }
    }
    /**
     * Resets the timer to 0, but continues to tick
     */
    public void reset() {
        mark = count;
        running = true;
    }
    /**
     * Resets the timer to 0, but continues to tick
     */
    public void mark() {
        mark = count;
        running = true;
    }
    /**
     * Stops the timer completely, keeping the value it has last
     */
    public void stop() {
        save = actsPassed();
        running = false;
    }
    /**
     * Stops the timer completely, resetting it to 0.
     */
    public void fullStop() {
        save = 0;
        running = false;
    }
    /**
     * Return the read of this timer.
     * @return the read of this timer (acts passed)
     */
    public long actsPassed() {
        if (running) return count - mark;
        else return save;
    }
    /**
     * Return the read of this timer.
     * @return the read of this timer (acts passed)
     */
    public long acts() {
        if (running) return count - mark;
        else return save;
    }
    /**
     * This method should be run once every act. Preferably in the World's act. Otherwise, timer's won't work.
     */
    public static void tick() {
        count++;
    }
}